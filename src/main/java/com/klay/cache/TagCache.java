package com.klay.cache;

import com.klay.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TagCache {
    public static List<TagDto> get() {
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto program = new TagDto();
        program.setCategoryName("开发语言");
        program.setTags(Arrays.asList("java", "node.js", "python", "golang", "c++", "c", "javaScript", "spring", "springboot", "django", "flask", "c#", "swoole", "ruby", "asp.net", "ruby-on-rails", "scala", "rust", "lavarel", "爬虫"));
        tagDtos.add(program);

        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel", "spring", "express", "django", "flask", "yii", "ruby-on-rails", "tornado", "koa", "struts"));
        tagDtos.add(framework);

        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux", "nginx", "docker", "apache", "运维", "centos", "ubuntu", "服务器", "负载均衡", "ssh", "容器", "jenkins", "vagrant", "devops", "debian", "fabric"));
        tagDtos.add(server);

        TagDto db = new TagDto();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql", "redis", "数据库", "sql", "mongodb", "json", "elasticsearch", "nosql", "memcached", "postgresql", "sqlite", "mariadb"));
        tagDtos.add(db);

        TagDto tool = new TagDto();
        tool.setCategoryName("开发工具");
        tool.setTags(Arrays.asList("git", "github", "macos", "visual-studio-code", "windows", "vim", "sublime-text", "intellij-idea", "phpstorm", "eclipse", "webstorm", "编辑器", "svn", "visual-studio", "pycharm", "emacs"));
        tagDtos.add(tool);

        return tagDtos;
    }

    public static String filterInvalid(String tags) {
        String[] split = StringUtils.split(tags, ",");
        List<TagDto> tagDtos = get();
        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid = Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}
