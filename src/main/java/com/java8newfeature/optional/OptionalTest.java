package com.java8newfeature.optional;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * @decription: optional测试
 * @author: 180449
 * @date 2021/7/14 11:11
 */
public class OptionalTest {

    /**
     * 1. 创建Optional实例
     */

    @Test(expected = NoSuchElementException.class)
    public void whenCreateEmptyOptional_thenNull() {
        Optional<User> emptyOpt = Optional.empty();
        emptyOpt.get();
    }

    /**
     * 明确对象不为空的时候使用of ，否则抛出NullPointerException
     * 如果对象可能为null或者非null，可以使用ofNullable
     */
    @Test(expected = NullPointerException.class)
    public void whenCreateOfEmptyOptional_thenNullPointerException() {
        User user = null;
        //Optional<User> opt = Optional.of(user);

        Optional<User> opt1 = Optional.ofNullable(user);
    }


    /**
     * 2. 访问 Optional 对象的值
     */

    /**
     * get 方法可能会抛出 空指针异常
     */
    @Test
    public void whenCreateOfNullableOptional_thenOk() {
        String name = "John";
        Optional<String> opt = Optional.ofNullable(name);

        assertEquals("John", opt.get());
    }

    @Test
    public void whenCheckIfPresent_thenOk() {
        User user = new User("john@gmail.com", "1234");
        Optional<User> opt = Optional.ofNullable(user);
        // 先校验对象是否为空
        //assertTrue(opt.isPresent());
        opt.ifPresent( u -> assertEquals(user.getEmail(), u.getEmail()));

        assertEquals(user.getEmail(), opt.get().getEmail());
    }

    /**
     *
     * orElse 与 orElseGet 的区别
     * 1. 对象为空而返回默认对象时，行为并无差异。
     * 2. 对象不为空时，orElse仍然创建对象，orElseGet不创建对象
     *    在执行较密集的调用时，比如调用 Web 服务或数据查询，这个差异会对性能产生重大影响。
     *
     */

    @Test
    public void givenEmptyValue_whenCompare_thenOk() {
        User user = null;
        System.out.println("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        System.out.println("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
    }

    @Test
    public void givenPresentValue_whenCompare_thenOk() {
        User user = new User("john@gmail.com", "1234");
        System.out.println("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        System.out.println("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
    }

    private User createNewUser() {
        System.out.println("Creating New User");
        return new User("extra@gmail.com", "1234");
    }

    /**
     * 3. 返回异常
     * 可以自定义返回的异常，丰富了语义
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenThrowException_thenOk() {
        User user = null;
        User result = Optional.ofNullable(user)
                .orElseThrow(IllegalArgumentException::new);
    }


    /**
     * 4.转换值
     */

    /**
     *
     */
    @Test
    public void whenMap_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        String email = Optional.ofNullable(user)
                .map(u -> u.getEmail()).orElse("default@gmail.com");

        assertEquals(email, user.getEmail());
    }

    @Test
    public void whenFilter_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        Optional<User> result = Optional.ofNullable(user)
                .filter(u -> u.getEmail() != null && u.getEmail().contains("@"));

        assertTrue(result.isPresent());
    }

}
