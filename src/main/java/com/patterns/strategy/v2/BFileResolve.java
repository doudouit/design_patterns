package com.patterns.strategy.v2;

public class BFileResolve implements IFileStrategy {
   
    @Override
    public FileTypeResolveEnum gainFileType() {
        return FileTypeResolveEnum.File_B_RESOLVE;
    }


    @Override
    public void resolve(Object objectparam) {
        System.out.println("B 类型解析文件，参数：{}"+ objectparam);
      //B类型解析具体逻辑
    }
}