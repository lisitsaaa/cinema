package com.example.cinema.service;

import com.example.cinema.repository.HallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HallService {
    @Autowired
    private HallRepository hallRepository;
}
