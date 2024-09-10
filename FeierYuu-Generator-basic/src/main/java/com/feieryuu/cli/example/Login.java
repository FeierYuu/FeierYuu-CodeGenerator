package com.feieryuu.cli.example;

import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

public class Login implements Callable<Integer> {
    @Option(names = {"-u", "--user"}, description = "用户名",interactive = true,arity = "0..1")
    String user;

    @Option(names = {"-p", "--password"}, description = "密码", interactive = true,arity = "0..1")
    String password;

    @Option(names = {"-cp","--checkpassword"},description = "检查密码",interactive = true,arity = "0..1")
    String checkPassword;

    public Integer call() throws Exception {
        System.out.println("username = "+user);
        System.out.println("password = " + password);
        System.out.println("checkPassword = " + checkPassword);
        return 0;
    }

    public static void main(String[] args) {
        new CommandLine(new Login()).execute("-u","-p","-cp");
    }
}
