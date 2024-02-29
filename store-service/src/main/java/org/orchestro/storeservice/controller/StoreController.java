package org.orchestro.storeservice.controller;

import lombok.RequiredArgsConstructor;
import org.orchestro.storeservice.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

}
