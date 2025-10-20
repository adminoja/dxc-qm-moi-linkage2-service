package th.go.dxc.app.util;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Mono;
import th.go.dxc.app.model.Lk2Service;
import th.go.dxc.app.model.Lk2ServiceFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ServiceEntityFilter;
import th.go.dxc.share.commons.util.ObjectMapperService;

@Component
public class Linkage2ServiceImplMapper {
	private final ObjectMapperService mapper;
	private final ObjectMapper objectMapper;
	
	public Linkage2ServiceImplMapper(ObjectMapperService mapper, ObjectMapper objectMapper) {
		super();
		this.mapper = mapper;
		this.objectMapper = objectMapper;
	}
	
	public Pageable mapEntityPageable(Pageable pageable) {
		return pageable;
	}
	
	public Page<Lk2Service> mapModelPage(Page<Lk2ServiceEntity> entityPage) {
		return mapper.mapPage(entityPage, Lk2Service.class);
	}
	
	public Lk2ServiceEntityFilter mapEntityFilter(Lk2ServiceFilter filter) {
		return mapper.map(filter, Lk2ServiceEntityFilter.class);
	}
	
	public List<Lk2Service> mapAsList(List<Lk2ServiceEntity> entityList) {
		return mapper.mapAsList(entityList, Lk2Service.class);
	}
	
	/**
	 * แปลง Page<S> → Page<T> ภายใน Mono<Page<S>>
	 */
	public <S, T> Mono<Page<T>> mapPageMono(Mono<Page<S>> sourceMono, Class<T> targetClass) {
		return sourceMono.map(page -> {
			List<T> mappedList = page.getContent().stream()
					.map(item -> objectMapper.convertValue(item, targetClass))
					.collect(Collectors.toList());
			return new PageImpl<>(mappedList, page.getPageable(), page.getTotalElements());
		});
	}

	/**
	 * แปลง List<S> + Pageable → Page<T> และคืน Mono<Page<T>>
	 */
	public <S, T> Mono<Page<T>> mapListMono(List<S> sourceList, Pageable pageable, Class<T> targetClass) {
		List<T> mappedList = sourceList.stream().map(item -> mapper.map(item, targetClass))
				.collect(Collectors.toList());
		return Mono.just(new PageImpl<>(mappedList, pageable, mappedList.size()));
	}
}
