package com.clinic.pojo;

import com.clinic.pojo.Category;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.9.v20210604-rNA", date="2023-09-05T17:14:41")
@StaticMetamodel(Medicine.class)
public class Medicine_ { 

    public static volatile SingularAttribute<Medicine, String> name;
    public static volatile SingularAttribute<Medicine, Integer> id;
    public static volatile SingularAttribute<Medicine, Category> categoryId;

}