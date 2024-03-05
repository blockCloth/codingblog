package com.coding.blog.web.front;

import com.coding.blog.service.service.IPostTagService;
import com.coding.blog.service.service.IPostsService;
import com.coding.blog.service.service.ISiteService;
import com.coding.blog.service.service.ITermTaxonomyService;
import com.coding.blog.service.vo.ConfigVo;
import com.coding.blog.service.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @User Administrator
 * @CreateTime 2024/2/17 16:59
 * @className com.coding.blog.web.front.ConfigController
 */
@Api(tags = "全局信息配置")
@RestController
@RequestMapping("/front")
public class ConfigController {
    @Autowired
    private ISiteService siteService;
    @Autowired
    private IPostsService postsService;
    @Autowired
    private IPostTagService postTagService;
    @Autowired
    private ITermTaxonomyService taxonomyService;

    @ApiOperation("获取全局信息配置")
    @GetMapping("config")
    public ResultObject getWebConfig(){
        //获取站点配置信息
        ConfigVo configVo = new ConfigVo();
        configVo.setSite(siteService.list().get(0));
        configVo.setPostSize(postsService.list().size());
        configVo.setPostTagSize(postTagService.list().size());
        configVo.setTermTaxonomySize(taxonomyService.list().size());
        configVo.setBlogView(siteService.getBlogView());

        return ResultObject.success(configVo);
    }

    @ApiOperation("设置博客浏览量")
    @GetMapping("setBlogView")
    public ResultObject setBlogView(){
        //获取站点配置信息
        if (siteService.setBlogView()){
            return ResultObject.success();
        }
        return ResultObject.failed();
    }
}
