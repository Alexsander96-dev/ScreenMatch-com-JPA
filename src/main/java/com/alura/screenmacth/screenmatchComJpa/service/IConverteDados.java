package com.alura.screenmacth.screenmatchComJpa.service;

public interface IConverteDados {
    <T> T  obterDados(String json, Class<T> classe);
}
