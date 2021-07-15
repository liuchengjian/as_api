package com.liucj.as.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liucj.as.api.config.NeedLogin;
import com.liucj.as.api.entity.CategoryEntity;
import com.liucj.as.api.entity.ResponseCode;
import com.liucj.as.api.entity.ResponseEntity;
import com.liucj.as.api.entity.UserEntity;
import com.liucj.as.api.service.CategoryService;
import com.liucj.as.api.service.UserService;
import com.liucj.as.api.utils.DataUtil;
import com.liucj.as.api.utils.UserRedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
@Api(tags = {"Category"})
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "商品类别列表")
    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity category(@RequestParam(value = "pageIndex", defaultValue = "1") @ApiParam("起始页") int pageIndex,
                                   @RequestParam(value = "pageSize", required = true, defaultValue = "10")
                                   @ApiParam("每页显示的数量") int pageSize) {
        try {
            PageHelper.startPage(pageIndex, pageSize);
            List<CategoryEntity> list = categoryService.categories();
            return ResponseEntity.success(DataUtil.getPageData(list));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.errorMessage("查找类别列表失败");
        }
    }

    @ApiOperation(value = "添加商品类别模块")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity addCategory(@RequestParam(value = "categoryName") @ApiParam("商品类别名称") String categoryName) {
        try {
            categoryService.addCategory(categoryName);
            return ResponseEntity.success("添加商品类别成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.of(ResponseCode.RC_ERROR).setMessage("商品类别名称重复");
        }
    }

    @ApiOperation(value = "删除商品类别模块")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity removeCategory( @ApiParam("商品类别ID") @PathVariable String id) {
        try {
            categoryService.removeCategory(id);
            return ResponseEntity.success("删除商品类别成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.of(ResponseCode.RC_ERROR).setMessage("删除商品类别名称失败");
        }
    }

}
