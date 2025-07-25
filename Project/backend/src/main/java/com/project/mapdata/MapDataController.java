package com.project.mapdata;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mapdata")
@RequiredArgsConstructor
public class MapDataController {

	private final MapDataService mapDataService;
	
	
	
	
	}
	
}
