package com.huixinghua.criminalintent.database;

public class CrimeDbSchema {

    public static final  class  CrimeTable{//内部类用于定义描述数据表元素的String常量
        public static final String   NAME="crimes";//定义数据库表名CrimeTable.NAME

        public  static final  class  Cols{
            public static final  String UUID="uuid";
            public static final  String  TITLE="title";
            public static final  String DATE="date";
            public static final  String SOLVED="solved";
            public  static final String SUSPECT="suspect";


        }
    }


}
