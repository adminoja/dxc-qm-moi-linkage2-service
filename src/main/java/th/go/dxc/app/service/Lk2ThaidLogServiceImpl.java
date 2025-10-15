package th.go.dxc.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.extern.slf4j.Slf4j;
import th.go.dxc.app.model.Lk2ThaidLog;
import th.go.dxc.app.model.Lk2ThaidLogFilter;
import th.go.dxc.app.util.Lk2ThaidLogServiceImplMapper;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntity;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.entity.Lk2ThaidLogEntityFilter;
import th.go.dxc.infra.datasource.dxcsamdb.lk2.repository.Lk2ThaidLogRepository;

@Slf4j
public class Lk2ThaidLogServiceImpl implements Lk2ThaidLogService {
	
	private final  Lk2ThaidLogRepository repository;
	private final Lk2ThaidLogServiceImplMapper mapper;
	
	public Lk2ThaidLogServiceImpl(Lk2ThaidLogRepository repository, Lk2ThaidLogServiceImplMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}
	
	public Lk2ThaidLogEntity createLk2ThaidLogEntity(Lk2ThaidLog model) {
		Lk2ThaidLogEntity entity = new Lk2ThaidLogEntity();
		
		entity.setAccessToken(model.getAccessToken());
		entity.setExpiresIn(model.getExpiresIn());
		entity.setFirstname(model.getFirstname());
		entity.setIssuedAt(model.getIssuedAt());
		entity.setLastname(model.getLastname());
		entity.setLoginDatetime(model.getLoginDatetime());
		entity.setPid(model.getPid());
		entity.setRefreshToken(model.getRefreshToken());
		entity.setSessionStateKeycloak(model.getSessionStateKeycloak());
		entity.setStatus(model.getStatus());
		entity.setType(model.getType());
		entity.setUsername(model.getUsername());
		return entity;
	}

	@Override
	public Page<Lk2ThaidLog> findAll(Lk2ThaidLogFilter filter, Pageable pageable) {
		Lk2ThaidLogEntityFilter entityFilter = mapper.mapEntityFilter(filter);
		Pageable entityPageable = mapper.mapEntityPageable(pageable);
		Page<Lk2ThaidLogEntity> entityPage = repository.findByFilterNative(entityFilter, entityPageable);
		Page<Lk2ThaidLog> resultPage = mapper.mapModelPage(entityPage);
		if (log.isDebugEnabled()) log.debug("lk2ThaidLog findAll: {}", entityPage);
		return resultPage;
	}
	
	@Override
	public Lk2ThaidLogEntity insert(Lk2ThaidLog lk2ThaidLog) {
		Lk2ThaidLogEntity entity = new Lk2ThaidLogEntity();
		entity = createLk2ThaidLogEntity(lk2ThaidLog);
		if (log.isDebugEnabled()) log.debug("lk2ThaidLog insert: {}", entity);
		repository.save(entity);
		log.info("✅ บันทึก ThaID Token Log เรียบร้อย");
		return entity;
	}
}
