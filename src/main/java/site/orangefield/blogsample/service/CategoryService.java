package site.orangefield.blogsample.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.orangefield.blogsample.domain.category.Category;
import site.orangefield.blogsample.domain.category.CategoryRepository;

@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public void 카테고리등록(Category category) {
        categoryRepository.save(category);
    }
}
