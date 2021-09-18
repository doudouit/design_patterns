package com.patterns.chainOfResponsibility.v1;

import java.util.ArrayList;
import java.util.List;

/**
 * 责任链默认
 * 类似于递归调用
 */
public class ChainOfResponsibility {

    public static void main(String[] args) {
        Request request = new Request();
        request.str = "大家好：），<script>,欢迎mashibing.com，大家都是996";
        Response response = new Response();
        response.str = "";

        FilterChain filterChain = new FilterChain();
        filterChain.add(new SensitiveFilter()).add(new HtmlFilter());
        filterChain.doFilter(request, response);
        System.out.println(request.str);
        System.out.println(response.str);
    }

    static class Request{
        String str;
    }

    static class Response {
        String str;
    }

    interface Filter {
        boolean doFilter(Request request, Response response, FilterChain filterChain);
    }

    static class HtmlFilter implements Filter {

        @Override
        public boolean doFilter(Request request, Response response, FilterChain filterChain) {
            request.str = request.str.replaceAll("996", "955") + "HtmlFilter";
            filterChain.doFilter(request, response);
            response.str += "--HtmlFilter";
            return true;
        }
    }

    static class SensitiveFilter implements Filter {

        @Override
        public boolean doFilter(Request request, Response response, FilterChain filterChain) {
            request.str = request.str.replaceAll("<", "[").replaceAll(">", "]") + "SenvitiveFilter";
            filterChain.doFilter(request, response);
            response.str += "--SenvitiveFilter";
            return true;
        }
    }

    static class FilterChain {
        List<Filter> filters = new ArrayList<>();
        // 记录执行的位置
        int index = 0;

        public FilterChain add(Filter filter) {
            filters.add(filter);
            return this;
        }

        boolean doFilter(Request request, Response response){
            if (index == filters.size()) {
                return false;
            }
            Filter filter = filters.get(index);
            index++;

            return filter.doFilter(request, response, this);
        }
    }
}
