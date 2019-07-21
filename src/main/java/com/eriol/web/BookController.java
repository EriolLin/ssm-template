package com.eriol.web;

import java.util.List;

import com.eriol.entity.Appointment;
import com.eriol.service.AppointmentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.eriol.dto.AppointExecution;
import com.eriol.dto.Result;
import com.eriol.entity.Book;
import com.eriol.enums.AppointStateEnum;
import com.eriol.exception.NoNumberException;
import com.eriol.exception.RepeatAppointException;
import com.eriol.service.BookService;

@Controller
@RequestMapping("/book") // url:/模块/资源/{id}/细分 /seckill/list
public class BookController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BookService bookService;

	@Autowired
	private AppointmentService appointmentService;

//	,produces = "text/html;charset=UTF-8"
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	private String list() {
//		List<Book> list = bookService.getList();
//		Book book = new Book();
//		book.setBookId(1001);
//		book.setName("数据结构");
//		book.setNumber(10);
//		bookService.updateOne(book);

//		Appointment byKeyWithBook = appointmentService.getByKeyWithBook(1002, 3);


//		model.addAttribute("list", list);
		// list.jsp + model = ModelAndView
		//PageInfo<Book> bookPageInfo = new PageInfo<>(bookService.getList());
		Page page = PageHelper.startPage(1, 3);
		List<Book> list = bookService.getList();
		//bookPageInfo.getList().get(1)
		return list.toString();// WEB-INF/jsp/"list".jsp
	}

	@RequestMapping(value = "/{bookId}/detail", method = RequestMethod.GET)
	private Book detail(@PathVariable("bookId") Long bookId, Model model) {
		if (bookId == null) {
			return null;
		}
		Book book = bookService.getById(bookId);
		if (book == null) {
			return null;
		}
		model.addAttribute("book", book);
		return book;
	}

	// ajax json
	@RequestMapping(value = "/{bookId}/appoint", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	private Result<AppointExecution> appoint(@PathVariable("bookId") Long bookId, @RequestParam("studentId") Long studentId) {
		if (studentId == null || studentId.equals("")) {
			return new Result<>(false, "学号不能为空");
		}
		AppointExecution execution = null;
		try {
			execution = bookService.appoint(bookId, studentId);
		} catch (NoNumberException e1) {
			execution = new AppointExecution(bookId, AppointStateEnum.NO_NUMBER);
		} catch (RepeatAppointException e2) {
			execution = new AppointExecution(bookId, AppointStateEnum.REPEAT_APPOINT);
		} catch (Exception e) {
			execution = new AppointExecution(bookId, AppointStateEnum.INNER_ERROR);
		}
		return new Result<AppointExecution>(true, execution);
	}

}
