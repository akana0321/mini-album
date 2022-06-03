package com.mycompany.mini_album.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.mini_album.dto.Category;
import com.mycompany.mini_album.service.CategoryService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class BoardListController {
	
	@Resource
	private CategoryService categoryService;

	@GetMapping("/readCategoryList")
	public List<Category> readCategoryList(@RequestBody String mid){
		List<Category> categoryNames = categoryService.selectAll(mid);
		return categoryNames;
	}
	
	@PostMapping("/insertNewCategoryName")
	public int insertNewCategoryName(@RequestBody Category category) {
		int result = categoryService.insertCategory(category);
		
		return result;
	}

}
