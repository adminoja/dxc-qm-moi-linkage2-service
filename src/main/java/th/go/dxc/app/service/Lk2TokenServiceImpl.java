package th.go.dxc.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.extern.slf4j.Slf4j;
import th.go.dxc.app.model.Lk2TokenService;
import th.go.dxc.app.model.Lk2TokenServiceFilter;
import th.go.dxc.app.util.Lk2TokenServiceServiceImplMapper;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2TokenServiceEntityFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2TokenServiceRepository;

@Slf4j
public class Lk2TokenServiceImpl implements Lk2TokenServiceService {
	
	private final Lk2TokenServiceRepository repository;
	private final Lk2TokenServiceServiceImplMapper mapper;
	
	public Lk2TokenServiceImpl(Lk2TokenServiceRepository repository, Lk2TokenServiceServiceImplMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}
	
	private Lk2TokenServiceEntity createLk2TokenServiceEntity(Lk2TokenService model) {
		Lk2TokenServiceEntity entity = new Lk2TokenServiceEntity();
		entity.setUsername(model.getUsername());
		entity.setToken(model.getToken());
		entity.setInsertTime(model.getInsertTime());
		entity.setChannel(model.getChannel());
		entity.setSessionState(model.getSessionState());
		entity.setLastActiveTime(model.getLastActiveTime());
		return entity;
	}
	
	// -------------------- ค้นหา Lk2TokenService findAll -------------------- 
	@Override
	public Page<Lk2TokenService> findAll(Lk2TokenServiceFilter filter, Pageable pageable) {
		Lk2TokenServiceEntityFilter entityFilter = mapper.mapEntityFilter(filter);
		Pageable entityPageable = mapper.mapEntityPageable(pageable);
		Page<Lk2TokenServiceEntity> entityPage = repository.findByFilterNative(entityFilter, entityPageable);
		Page<Lk2TokenService> resultPage = mapper.mapModelPage(entityPage);
		if (log.isDebugEnabled()) log.debug("lk2ThaidLog findAll: {}", entityPage);
		return resultPage;
	}
	
	// -------------------- บันทึก Linkage2 Token -------------------- 
	@Override
	public Lk2TokenServiceEntity insert(Lk2TokenService lk2TokenService) {
		Lk2TokenServiceEntity entity = new Lk2TokenServiceEntity();
		entity = createLk2TokenServiceEntity(lk2TokenService);
		if (log.isDebugEnabled()) log.debug("lk2TokenService insert: {}", entity);
		repository.save(entity);
		log.info("✅ บันทึก Linkage2 Token Log เรียบร้อย");
		return entity;
	}
	
}
