package ma.emsi.smartwatering.api;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import ma.emsi.smartwatering.controller.RealTimeController;
import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.model.Grandeur;
import ma.emsi.smartwatering.model.Zone;
import ma.emsi.smartwatering.repository.GrandeurRepository;
import ma.emsi.smartwatering.service.AppUserService;
import ma.emsi.smartwatering.service.EspaceVertService;
import ma.emsi.smartwatering.service.GrandeurService;
import ma.emsi.smartwatering.service.ZoneService;

@RestController @RequestMapping("/grandeurs")  @RequiredArgsConstructor
public class GrandeurApiController {


	@Autowired
	GrandeurService grandeurService;
	@Autowired
	ZoneService zoneService;
	


}
