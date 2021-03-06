package com.web.store.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.store.exception.ProductNotFoundException;
import com.web.store.model.BookBean;
import com.web.store.model.CompanyBean;
import com.web.store.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	ProductService service;
	
	@Autowired
	ServletContext context;

	@RequestMapping("/products")
	public String list(Model model) {
		List<BookBean> list = service.getAllProducts();
		model.addAttribute("products", list);
		return "products";
	}

	@RequestMapping("/update/stock")
	public String updateStock(Model model) {
		service.updateAllStocks();
		return "redirect:/products";
	}

	@RequestMapping("/queryByCategory")
	public String getCategoryList(Model model) {
		List<String> list = service.getAllCategories();
		model.addAttribute("categoryList", list);
		return "types/category";
	}

	@RequestMapping("/products/{category}")
	public String getProductsByCategory(@PathVariable("category") String category, Model model) {
		List<BookBean> products = service.getProductsByCategory(category);
		model.addAttribute("products", products);
		return "products";
	}

	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") Integer id, Model model) {
		BookBean bb = service.getProductById(id);
		System.out.println(bb);
		model.addAttribute("product", service.getProductById(id));
		return "product";
	}
	@GetMapping("/products/add")
//	@RequestMapping(value = "/products/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model) {
		BookBean bb = new BookBean();
		bb.setAuthor("?????????");
		bb.setTitle("????????????");
		bb.setPrice(500.0);
	    model.addAttribute("bookBean", bb); 
		return "addProduct";
	}
	@PostMapping("/products/add")
	public String processAddNewProductForm	
	        (@ModelAttribute("bookBean") BookBean bb, 
			RedirectAttributes  ra,
			BindingResult result ) {
		String[] suppressedFields = result.getSuppressedFields();
		if (suppressedFields.length > 0) {
			throw new RuntimeException("??????????????????????????????: " + 
		    StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}
		BookBeanValidator   validator = new BookBeanValidator();
		validator.validate(bb, result);
		if (result.hasErrors()) {
			return "addProduct";
		}
		
		if (bb.getStock() == null) {
			bb.setStock(0);
		}
//-----------------------------------
		MultipartFile productImage = bb.getProductImage();
		String originalFilename = productImage.getOriginalFilename();
		bb.setFileName(originalFilename);
		
		// SNOOPY.jpeg
		
		String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
		String rootDirectory = context.getRealPath("/");
		//  ??????Blob??????????????? Hibernate ???????????????
		if (productImage != null && !productImage.isEmpty() ) {
			try {
				byte[] b = productImage.getBytes();
				Blob blob = new SerialBlob(b);
				bb.setCoverImage(blob);
			} catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException("????????????????????????: " + e.getMessage());
			}
		}
		
// -----------------------------------------
		CompanyBean cb = service.getCompanyById(bb.getCompanyId());
//		bb.setCompanyBean(cb);
		service.addProduct(bb);
		
	//  ??????????????????????????????????????????
			try {
				File imageFolder = new File(rootDirectory, "images0520");
				if (!imageFolder.exists()) imageFolder.mkdirs();
				File file = new File(imageFolder, bb.getBookId() + ext);
				productImage.transferTo(file);
			} catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException("????????????????????????: " + e.getMessage());
			}
		
		
		ra.addFlashAttribute("InsertSuccess", "????????????-2021-05-19");
		return "redirect:/products";
	}
	
//	@ModelAttribute("companyList")
//	public Map<Integer, String> getCompanyList() {
//	    Map<Integer, String> companyMap = new HashMap<>();
//	    List<CompanyBean> list = service.getCompanyList();
//	    for (CompanyBean cb : list) {
//	        companyMap.put(cb.getId(), cb.getName());
//	    }
//	    return companyMap;
//	}
//	
//	@ModelAttribute("companyList")
//	public List<CompanyBean> getCompanyList() {
//	    List<CompanyBean> list = service.getCompanyList();
//	    return list;
//	}
//	
//	
//	@ModelAttribute("categoryList")
//	public List<String> getCategoryList() {
//	    return service.getAllCategories();
//	}
	
	@ModelAttribute
	public void getFormData(Model model) {
	    List<CompanyBean> companies = service.getCompanyList();
	    model.addAttribute("companyList", companies);
	    
	    List<String> categories =  service.getAllCategories();
	    model.addAttribute("categoryList", categories);
	    return ;
	}
	
	@InitBinder
	public void whiteListing(WebDataBinder binder) {
	    binder.setAllowedFields(
	    "author", 
	    "bookNo", 
	    "category", 
	    "price", 
	    "title", 
	    "productImage",
	    "companyId"
	    );
	}
	
	
	// return "forward:/anotherFWD": ??????(forward)?????????????????? /anotherFWD??????????????????
	// ???????????????????????????????????????????????????
	// return "anotherFWD": ??????????????????Spring????????????anotherFWD?????????????????????????????????
	// ???????????????????????????????????????????????????    
	
	// http://localhost:8080/mvcExercise/products/add?qty=100&name=Mary
	// Request URL: http://localhost:8080/mvcExercise/products/add
	// Request URI: /mvcExercise/products/add
	
	
	// http://localhost:8080/mvcExercise/forwardDemo
	// Request URL:http://localhost:8080/mvcExercise/forwardDemo
	// Request URI: /mvcExercise/forwardDemo
	
	@RequestMapping(value = "/forwardDemo")
	public String forward(Model model, HttpServletRequest request, HttpServletResponse response) {
	    String uri = request.getRequestURI();
	    StringBuffer url = request.getRequestURL();
	    model.addAttribute("modelData0", "?????????/forwardDemo???????????????");
	    model.addAttribute("uri0", uri);
	    model.addAttribute("url0", url.toString());
	    
//	    RequestDispatcher rd = request.getRequestDispatcher("/hello.jsp");
//	    try {
//			rd.forward(request, response);
//		} catch (ServletException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	    
	    
	    
	    return "forward:/anotherFWD";
	}
	// ?????????????????????????????????????????????????????????????????????
	@RequestMapping(value = "/anotherFWD")
	public String forwardA(Model model, HttpServletRequest request) {
	    String uri = request.getRequestURI();
	    model.addAttribute("modelData1", "?????????/anotherFWD???????????????");
	    model.addAttribute("uri1", uri);
	    return "forwardedPage";
	}
	// return "redirect:/redirectAnother": ??????????????????????????? /redirectAnother???????????????????????????
	// (redirect)????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
	// ??? RedirectAttributes???????????????????????????????????????????????????????????????????????????????????????????????????
	@RequestMapping(value = "/redirectDemo")
	public String redirect(Model model, RedirectAttributes redirectAttributes, 
	                    HttpServletRequest request) {
	    String uri = request.getRequestURI();
	    model.addAttribute("modelData2", "?????????/redirectDemo??????????????????????????????????????????"
	                        + "???????????????????????????????????????????????????????????????-0520-1");
	    model.addAttribute("uri2", uri + " -0520-1");
	    redirectAttributes.addFlashAttribute("modelData3", "????????????RedirectAttributes"
	                        + "?????????????????????????????????????????????-0520-2");
	    redirectAttributes.addFlashAttribute("uri3", uri  + " -0520-2");
	    return "redirect:/redirectAnother";
	}
	//-------------------------
	// ?????????????????????????????????????????????????????????????????????????????????
	@RequestMapping(value = "/redirectAnother")
	public String redirectA(Model model, HttpServletRequest request) {
	    return "redirectedPage";
	}

	@RequestMapping(value = "/getPicture/{bookId}", method = RequestMethod.GET)
	                       
	public ResponseEntity<byte[]> getPicture(
			HttpServletResponse resp, 
			@PathVariable Integer bookId
	) {
		String filePath = "/resources/images/NoImage.jpg";

		byte[] media = null;
		HttpHeaders headers = new HttpHeaders();
		String filename = "";
		int len = 0;
		BookBean bean = service.getProductById(bookId);
		if (bean != null) {
			Blob blob = bean.getCoverImage();
			filename = bean.getFileName();
			if (blob != null) {
				try {
					len = (int) blob.length();
					media = blob.getBytes(1, len);
				} catch (SQLException e) {
					throw new RuntimeException("ProductController???getPicture()??????SQLException: " + e.getMessage());
				}
			} else {
				media = toByteArray(filePath);
				filename = filePath;
			}
		} else {
			media = toByteArray(filePath);
			filename = filePath;
		}
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		String mimeType = context.getMimeType(filename);
		MediaType mediaType = MediaType.valueOf(mimeType);
		System.out.println("mediaType =" + mediaType);
		headers.setContentType(mediaType);
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
		return responseEntity;
	}

	private byte[] toByteArray(String filepath) {
		byte[] b = null;
		String realPath = context.getRealPath("/abc/def/xyz.asd");
//		System.out.println("realPath=" + realPath);
		realPath = context.getRealPath(filepath);
		try {
			File file = new File(realPath);
			long size = file.length();
			System.out.println("size=" + size);
			b = new byte[(int) size];
			InputStream fis = context.getResourceAsStream(filepath);
			fis.read(b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
	@ExceptionHandler({ProductNotFoundException.class, SQLException.class})
	public ModelAndView handleError(HttpServletRequest request, 
							ProductNotFoundException exception) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("invalidBookId", exception.getBookId());
		mv.addObject("exception", exception);
		mv.addObject("message", exception.getMessage());
		mv.addObject("url", request.getRequestURL() + "?" + request.getQueryString());
		mv.setViewName("productNotFound");
		return mv;
	}
}



