package com.inkronsane.Library.entity;


import java.io.*;

public interface BaseEntity<T extends Serializable> {

   T getId();

   void setId(T id);
}
