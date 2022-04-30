package com.example.FlightManagment.dto;

import java.util.LinkedList;
import java.util.List;

public class ApiResult<T> {
     T Result;
     List<String> Errors = new LinkedList<>();

     public boolean isError() {
          return !Errors.isEmpty();
     }

     public void addError(String errorMsg) {
          Errors.add(errorMsg);
     }

     public List<String> getErrors() {
          return Errors;
     }

     public T getResult() {
          return Result;
     }

     public void setResult(T result) {
          Result = result;
     }
}
