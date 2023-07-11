package com.example.cinema.service;

public interface AbstractService<T> {
    T save(T t);
    void remove(long id);
    T findById(long id);
}
