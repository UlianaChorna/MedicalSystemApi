package com.uliana.MedicalSystemApi.service;

public interface BaseCRUDService<T> {
   T create(T t);
    T getById(Long id);
}
