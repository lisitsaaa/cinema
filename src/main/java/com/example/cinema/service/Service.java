package com.example.cinema.service;

public interface Service<T> {
    T save(T t);
    void remove(long id);
    T findById(long id);
}
