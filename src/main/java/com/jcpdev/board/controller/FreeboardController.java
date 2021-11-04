package  com.jcpdev.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jcpdev.board.model.Board;
import com.jcpdev.board.model.PageDto;
import com.jcpdev.board.service.CommentService;
import com.jcpdev.board.service.FreeboardService;

@Controller
@RequestMapping("/community")
public class FreeboardController {
	private static final Logger logger = LoggerFactory.getLogger(FreeboardController.class);
	
	@Autowired
	FreeboardService service;
	
	@Autowired
	CommentService  cmtservice;
	
	//게시판 리스트 보기(검색기능 포함)
	@RequestMapping(value={"/","/list"})
	public void pageList(@RequestParam Map<String, String> param,Model model) { //String page,String field,String findText,Model model) {
		logger.info("**freeboard list 출력합니다.");
		
		int currentPage;//현재 페이지
		List<Board> list;
		int totalCount; int pageSize=10;
		String page=param.get("page");
		if(page==null || page.trim().length()==0) currentPage = 1;
		else currentPage = Integer.parseInt(page);   //page파라미터가 숫자로 넘어온경우만 실행. 
		
		//page를 사용자 맘대로 문자 대입하면 NumberFormatExceptrion 발생 -> ExceptionHandler 로 처리합니다.
		
		PageDto pageDto;
		//검색 기능사용할 때 검색필드와 검색키워드 뷰에 전달한다.

		String findText = param.get("findText");
		String field=param.get("field");
		
		totalCount=service.searchCount(param);   //서비스 메소드 타입 변경예정
		pageDto=new PageDto(currentPage, pageSize, totalCount, field, findText);
		list=service.searchList(pageDto);  //주석처리 예정
		Map<String,Object> map = new HashMap<String,Object>();    
		map.put("field",field);
		map.put("findText",findText);
		map.put("page", pageDto);			//view에게 전달할 모델객체 설정
		map.put("list",list);				//          "
		model.addAllAttributes(map);	//위에 4개의 put 실행한 map객체를 애트리뷰트에 저장한다.
	}  //view 이름은? list.jsp
	
	
	
	
	
	
	//상세보기 : 미구현
	@RequestMapping("/detail")     
	public void detail() {
	
	}
	
	//글쓰기 - view  : insert() 메소드 
	@RequestMapping(value="/insert")
	public void insert(int page,Model model) {
		model.addAttribute("page", page);
	}  //view이름은 insert
	
	//글쓰기 - 저장   : save()메소드  리다이렉트 list로.
	@RequestMapping(value="/save")
	public String save(@ModelAttribute Board board) {
		service.insert(board);
		
		return "redirect:/community/list";
	}
	
	//수정
	@RequestMapping(value = "update", method = RequestMethod.GET)
	public void update(@RequestParam Map<String, String> param,Model model) {		//@RequestParam Map<String, String> param
		model.addAttribute("bean", service.getBoardOne(Integer.parseInt(param.get("idx"))));
	}
	
	//삭제 : 미구현
	
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleErr(HttpServletRequest request ) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", request.getRequestURL());   //애트리뷰트 저장
		mav.setViewName("/error/error");
		return mav;
	}
	
	
	
}
