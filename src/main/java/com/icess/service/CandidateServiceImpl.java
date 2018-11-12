package com.icess.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jinq.tuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.icess.JinqSource;
import com.icess.entity.Candidate;
import com.icess.entity.CandidateSkill;
import com.icess.entity.Marks;
import com.icess.entity.Role;
import com.icess.entity.Skill;
import com.icess.entity.SubSkill;
import com.icess.entity.Test;
import com.icess.entity.User;
import com.icess.model.CustomTest;
import com.icess.repository.CandidateRepository;
import com.icess.repository.CandidateSkillRepository;
import com.icess.repository.UserRepository;

@Component("CandidateService")
@Transactional
public class CandidateServiceImpl implements CandidateService {
	
	@Autowired
	private JinqSource source;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private CandidateRepository candidateRepository;
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private CandidateSkillRepository candidateSkillRepository;
	
//	@Override
//	@Async
//	public Future<Boolean> addEditCandidate(Candidate candidate) {
//		try {
//				if(candidate.getCandidateId() == 0) {
//					User user = userRepository.saveAndFlush(candidate.getUser());
//					candidate.getUser().setRole(user.getRole());
//				}
//				candidate.setReg_date(new Date());	
//				candidate.setCandidatestatusFlag(false);
//				Candidate can = candidateRepository.saveAndFlush(candidate);
//								
//				for(int i=0;i<candidate.getCandidateskills().size();i++) {
//					CandidateSkill candidateSkill = candidate.getCandidateskills().get(i);
//					
//					if(candidate.getCandidateskills().get(i).getCandidateskillId()!=0) {
//						candidateSkill.setCandidateskillId(candidate.getCandidateskills().get(i).getCandidateskillId());
//					}
//					candidateSkill.setSkillName(getSkillById(candidateSkill.getSkillId()).getSkill());
//					candidateSkill.setSubSkillName(getSubSkillById(candidateSkill.getSubskillId()).getSubSkill());
//					if(candidate.getCandidateskills().get(i).getCandidateskillId()==0) {
//						candidateSkill.setIsDeleted("N");
//					}else {
//						candidateSkill.setIsDeleted(candidate.getCandidateskills().get(i).getIsDeleted());
//					}
//					candidateSkill.setCandidate(can);
//					
//					candidateSkillRepository.saveAndFlush(candidateSkill);
//				}
//				
//				return new AsyncResult<Boolean>(true);
//		}catch (Exception e) {
//			e.printStackTrace();
//			return new AsyncResult<Boolean>(false);
//		}
//	}

	@Override
	@Async
	public Future<Candidate> addEditCandidate(Candidate candidate) {
		try {
				if(candidate.getCandidateId() == 0) {
					User user = userRepository.saveAndFlush(candidate.getUser());
					candidate.getUser().setRole(user.getRole());
				}
				candidate.setReg_date(new Date());	
				candidate.setCandidatestatusFlag(false);
				Candidate can = candidateRepository.saveAndFlush(candidate);
								
				for(int i=0;i<candidate.getCandidateskills().size();i++) {
					CandidateSkill candidateSkill = candidate.getCandidateskills().get(i);
					
					if(candidate.getCandidateskills().get(i).getCandidateskillId()!=0) {
						candidateSkill.setCandidateskillId(candidate.getCandidateskills().get(i).getCandidateskillId());
					}
					candidateSkill.setSkillName(getSkillById(candidateSkill.getSkillId()).getSkill());
					candidateSkill.setSubSkillName(getSubSkillById(candidateSkill.getSubskillId()).getSubSkill());
					if(candidate.getCandidateskills().get(i).getCandidateskillId()==0) {
						candidateSkill.setIsDeleted("N");
					}else {
						candidateSkill.setIsDeleted(candidate.getCandidateskills().get(i).getIsDeleted());
					}
					candidateSkill.setCandidate(can);
					
					candidateSkillRepository.saveAndFlush(candidateSkill);
				}
				
				return new AsyncResult<Candidate>(can);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	@Async
	public Future<Candidate> getCandidateById(int candidateId) {
		try {
			Candidate candidate = source.streamAll(entityManager, Candidate.class)
				  .where(tt->tt.getCandidateId()==candidateId)
				  .map(o->new Candidate() {{
					  setCandidateId(o.getCandidateId());
					  setCandidateName(o.getCandidateName());
					  setAddress(o.getAddress());
					  setContact_No1(o.getContact_No1());
					  setContact_No2(o.getContact_No2());
					  setEmail(o.getEmail());
					  setReg_date(o.getReg_date());
					  setUser(new User() {{
						  setUserId(o.getUser().getUserId());
						  setUserName(o.getUser().getUserName());
						  setRole(new Role() {{
							  setRoleId(o.getUser().getRole().getRoleId());
							  setRoleName(o.getUser().getRole().getRoleName());
						  }});
					  }});
					  setCandidateskills(getCandidateSubSkillList(candidateId));
					  setCandidatestatusFlag(o.getCandidatestatusFlag());
					  setUploadFile(o.getUploadFile());
					  setUploadPath(o.getUploadPath());
					  setUpload_img_file(o.getUpload_img_file());
				  }})
				  .collect(Collectors.toList()).get(0);
			
			return new AsyncResult<Candidate>(candidate);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	@Override
//	@Async
//	public Future<Candidate> getCandidateById(int candidateId) {
//		try {
//			Candidate candidate = source.streamAll(entityManager, Candidate.class)
//				  .where(tt->tt.getCandidateId()==candidateId)
//				  .map(o->new Candidate() {{
//					  setCandidateId(o.getCandidateId());
//					  setCandidateName(o.getCandidateName());
//					  setAddress(o.getAddress());
//					  setContact_No1(o.getContact_No1());
//					  setContact_No2(o.getContact_No2());
//					  setEmail(o.getEmail());
//					  setReg_date(o.getReg_date());
//					  setUser(new User() {{
//						  setUserId(o.getUser().getUserId());
//					  }});
//					  setCandidateskills(getCandidateSubSkillList(candidateId));
//					  setCandidatestatusFlag(o.getCandidatestatusFlag());
//				  }})
//				  .collect(Collectors.toList()).get(0);
//			
//			return new AsyncResult<Candidate>(candidate);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	@Override
	@Async
	public Future<List<Candidate>> getAllCandidate() {
		try {
				List<Candidate> candidateList = source.streamAll(entityManager, Candidate.class)
								.map(o->new Candidate() {{
									  setCandidateId(o.getCandidateId());
									  setCandidateName(o.getCandidateName());
									  setAddress(o.getAddress());
									  setContact_No1(o.getContact_No1());
									  setContact_No2(o.getContact_No2());
									  setEmail(o.getEmail());
									  setReg_date(o.getReg_date());
									  setUser(new User() {{
										  setUserId(o.getUser().getUserId());
									  }});
									  setCandidateskills(getCandidateSubSkillList(o.getCandidateId()));
									  setCandidatestatusFlag(o.getCandidatestatusFlag());
								  }})
								.collect(Collectors.toList());
				
				return new AsyncResult<List<Candidate>>(candidateList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<CandidateSkill> getCandidateSubSkillList(int candidateId){
		try{
			List<CandidateSkill> candidateSkillList = source.streamAll(entityManager, CandidateSkill.class)
				.join((tt,source)->source.stream(Skill.class))
				.where(tt->tt.getOne().getSkillId() == tt.getTwo().getSkillId() &&
						   tt.getOne().getIsDeleted().equals("N"))
				.join((tt,source)->source.stream(SubSkill.class))
				.where(tt->tt.getOne().getOne().getSubskillId() == tt.getTwo().getSubSkillId() 
						&& tt.getOne().getOne().getCandidate().getCandidateId() == candidateId)
				
				.map(ss->new CandidateSkill() {{
					setCandidateskillId(ss.getOne().getOne().getCandidateskillId());
					setSkillName(ss.getOne().getTwo().getSkill());
					setSkillId(ss.getOne().getOne().getSkillId());
					setSubskillId(ss.getOne().getOne().getSubskillId());
					setSubSkillName(ss.getTwo().getSubSkill());
					setIsDeleted(ss.getOne().getOne().getIsDeleted());
				}})
				.collect(Collectors.toList());
			return candidateSkillList;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Skill getSkillById(int skillId) {
		try {
				Skill skill = source.streamAll(entityManager, Skill.class)
								.where(f->f.getSkillId()==skillId)
								.map(o-> new Skill() {{
									setSkillId(o.getSkillId());
									setSkill(o.getSkill());
								}})
								.collect(Collectors.toList()).get(0);
				return skill;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SubSkill getSubSkillById(int subSkillId) {
		try {
				SubSkill subSkill = source.streamAll(entityManager, SubSkill.class)
									.where(f->f.getSubSkillId()==subSkillId)
									.map(o-> new SubSkill() {{
										setSubSkillId(o.getSubSkillId());
										setSubSkill(o.getSubSkill());
									}})
									.collect(Collectors.toList()).get(0);
				return subSkill;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Future<List<Candidate>> getCandidateSearch(int skillId, int subSkillId, String name, String contactNo) {
		try {
			List<Pair<Candidate,CandidateSkill>> pairList = source.streamAll(entityManager, Candidate.class)
					.join((tt,source)->source.stream(CandidateSkill.class))
					.where(a->a.getOne().getCandidateId() == a.getTwo().getCandidate().getCandidateId())
					.toList();

			List<Candidate> candidateList = new ArrayList<Candidate>();
			if(skillId != 0) {
				candidateList = pairList.stream()
								.filter(a-> a.getTwo().getCandidateskillId() == skillId)
								.map(b-> new Candidate() {{
									setCandidateId(b.getOne().getCandidateId());
									setCandidateName(b.getOne().getCandidateName());
									setAddress(b.getOne().getAddress());
									setContact_No1(b.getOne().getContact_No1());
									setContact_No2(b.getOne().getContact_No2());
									setEmail(b.getOne().getEmail());
									setReg_date(b.getOne().getReg_date());
									setUser(new User() {{
										  setUserId(b.getOne().getUser().getUserId());
									}});
									setCandidateskills(getCandidateSubSkillList(b.getOne().getCandidateId()));
									setCandidatestatusFlag(b.getOne().getCandidatestatusFlag());
								}})
								.collect(Collectors.toList());
			}
			if(subSkillId != 0) {
				candidateList = pairList.stream()
						.filter(a-> a.getTwo().getSkillId() == skillId && 
									a.getTwo().getSubskillId() == subSkillId)
						.map(b-> new Candidate() {{
							setCandidateId(b.getOne().getCandidateId());
							setCandidateName(b.getOne().getCandidateName());
							setAddress(b.getOne().getAddress());
							setContact_No1(b.getOne().getContact_No1());
							setContact_No2(b.getOne().getContact_No2());
							setEmail(b.getOne().getEmail());
							setReg_date(b.getOne().getReg_date());
							setUser(new User() {{
								  setUserId(b.getOne().getUser().getUserId());
							}});
							setCandidateskills(getCandidateSubSkillList(b.getOne().getCandidateId()));
							setCandidatestatusFlag(b.getOne().getCandidatestatusFlag());
						}})
						.collect(Collectors.toList());
			}
			if(name != null) {
				if(skillId != 0 || subSkillId != 0) {
					candidateList = candidateList.stream()
							.filter(a-> a.getCandidateName().equals(name))
							.map(b-> new Candidate() {{
								setCandidateId(b.getCandidateId());
								setCandidateName(b.getCandidateName());
								setAddress(b.getAddress());
								setContact_No1(b.getContact_No1());
								setContact_No2(b.getContact_No2());
								setEmail(b.getEmail());
								setReg_date(b.getReg_date());
								setUser(new User() {{
									  setUserId(b.getUser().getUserId());
								}});
								setCandidateskills(getCandidateSubSkillList(b.getCandidateId()));
								setCandidatestatusFlag(b.getCandidatestatusFlag());
							}})
							.collect(Collectors.toList());
				}else {
					candidateList = pairList.stream()
							.filter(a-> a.getOne().getCandidateName().equals(name))
							.map(b-> new Candidate() {{
								setCandidateId(b.getOne().getCandidateId());
								setCandidateName(b.getOne().getCandidateName());
								setAddress(b.getOne().getAddress());
								setContact_No1(b.getOne().getContact_No1());
								setContact_No2(b.getOne().getContact_No2());
								setEmail(b.getOne().getEmail());
								setReg_date(b.getOne().getReg_date());
								setUser(new User() {{
									  setUserId(b.getOne().getUser().getUserId());
								}});
								setCandidateskills(getCandidateSubSkillList(b.getOne().getCandidateId()));
								setCandidatestatusFlag(b.getOne().getCandidatestatusFlag());
							}})
							.collect(Collectors.toList());
				}
			}
			if(contactNo != null) {
				if(skillId != 0 || subSkillId != 0 || name != null) {
					candidateList = candidateList.stream()
							.filter(a-> a.getContact_No1().equals(contactNo) || a.getContact_No2().equals(contactNo))
							.map(b-> new Candidate() {{
								setCandidateId(b.getCandidateId());
								setCandidateName(b.getCandidateName());
								setAddress(b.getAddress());
								setContact_No1(b.getContact_No1());
								setContact_No2(b.getContact_No2());
								setEmail(b.getEmail());
								setReg_date(b.getReg_date());
								setUser(new User() {{
									  setUserId(b.getUser().getUserId());
								}});
								setCandidateskills(getCandidateSubSkillList(b.getCandidateId()));
								setCandidatestatusFlag(b.getCandidatestatusFlag());
							}})
							.collect(Collectors.toList());
				}else {
					candidateList = pairList.stream()
							.filter(a-> a.getOne().getContact_No1().equals(contactNo) || a.getOne().getContact_No2().equals(contactNo))
							.map(b-> new Candidate() {{
								setCandidateId(b.getOne().getCandidateId());
								setCandidateName(b.getOne().getCandidateName());
								setAddress(b.getOne().getAddress());
								setContact_No1(b.getOne().getContact_No1());
							    setContact_No2(b.getOne().getContact_No2());
								setEmail(b.getOne().getEmail());
								setReg_date(b.getOne().getReg_date());
								setUser(new User() {{
									  setUserId(b.getOne().getUser().getUserId());
								}});
								setCandidateskills(getCandidateSubSkillList(b.getOne().getCandidateId()));
								setCandidatestatusFlag(b.getOne().getCandidatestatusFlag());
							}})
							.collect(Collectors.toList());
				}
			}
			
			return new AsyncResult<List<Candidate>>(candidateList);
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return null;
	}
	
	public Future<List<Candidate>> getAllPendingTestCandidate(){
		
		try {
			List<Candidate> candidatePendingTestList = source.streamAll(entityManager, Candidate.class)
			.where(pt->pt.getCandidatestatusFlag()==false)
			.map(o->new Candidate() {{
				  setCandidateId(o.getCandidateId());
				  setCandidateName(o.getCandidateName());
				  setAddress(o.getAddress());
				  setContact_No1(o.getContact_No1());
				  setContact_No2(o.getContact_No2());
				  setEmail(o.getEmail());
				  setReg_date(o.getReg_date());
				  setUser(new User() {{
					  setUserId(o.getUser().getUserId());
				  }});
				  setCandidateskills(getCandidateSubSkillList(o.getCandidateId()));
				  setCandidatestatusFlag(o.getCandidatestatusFlag());
			  }})
			.collect(Collectors.toList());
			
			return new AsyncResult<List<Candidate>>(candidatePendingTestList);	
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
//	public Future<List<Candidate>> getAllPendingEvaluationTestCandidate(){
//		
//		try {
//			List<Candidate> candidateEvaluationTest= source.streamAll(entityManager, Candidate.class)
//						.join((tt,source)->source.stream(Test.class))
//					   .where(tt->tt.getOne().getCandidateId()==tt.getTwo().getCandidate().getCandidateId()
//					   && tt.getTwo().getIstestFlag()==false)
//					   .map(o->new Candidate() {{
//							  setCandidateId(o.getOne().getCandidateId());
//							  setCandidateName(o.getOne().getCandidateName());
//							  setAddress(o.getOne().getAddress());
//							  setContact_No1(o.getOne().getContact_No1());
//							  setContact_No2(o.getOne().getContact_No2());
//							  setEmail(o.getOne().getEmail());
//							  setReg_date(o.getOne().getReg_date());
//							  setUser(new User() {{
//								  setUserId(o.getOne().getUser().getUserId());
//							  }});
//							  setCandidateskills(getCandidateSubSkillList(o.getOne().getCandidateId()));
//							  setCandidatestatusFlag(o.getOne().getCandidatestatusFlag());
//							 
//					   }})
//					   .collect(Collectors.toList());	
//			return new AsyncResult<List<Candidate>>(candidateEvaluationTest);
//			
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
	public Future<List<CustomTest>> getAllPendingEvaluationTestCandidate(){
		
		try {
			List<CustomTest> candidateEvaluationTest= source.streamAll(entityManager, Candidate.class)
						.join((tt,source)->source.stream(Test.class))
					   .where(tt->tt.getOne().getCandidateId()==tt.getTwo().getCandidate().getCandidateId()
					   && tt.getTwo().getIstestFlag()==false)
					   .map(o->new CustomTest() {{
							setCandidateId(o.getOne().getCandidateId());
						    setCandidateName(o.getOne().getCandidateName());
						    setAddress(o.getOne().getAddress());
						    setEmail(o.getOne().getEmail());
						    setContact_No1(o.getOne().getContact_No1());
						    setContact_No2(o.getOne().getContact_No2());
						    setCandidateskills(getCandidateSubSkillList(o.getOne().getCandidateId()));
						    setTestId(o.getTwo().getTestId());
						    setTestDate(o.getTwo().getTestDate());
						    setTestCode(o.getTwo().getTestCode());
	 
					   }})
					   .collect(Collectors.toList());	
			return new AsyncResult<List<CustomTest>>(candidateEvaluationTest);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public Future<List<CustomTest>> getAllEvaluatedCandidate(){
	try{
		List<CustomTest> evaluatedCandidate= source.streamAll(entityManager, Candidate.class)	
		.join((tt,source)->source.stream(Test.class))
		 .where(tt->tt.getOne().getCandidateId()==tt.getTwo().getCandidate().getCandidateId()
		  && tt.getTwo().getIstestFlag()==true)
		 .join((tt,source)->source.stream(Marks.class))
		 .where(tt->tt.getOne().getOne().getCandidateId() == tt.getTwo().getCandidate().getCandidateId())
		 .map(o->new CustomTest() {{
				setCandidateId(o.getOne().getOne().getCandidateId());
				 setCandidateName(o.getOne().getOne().getCandidateName());
				    setAddress(o.getOne().getOne().getAddress());
				    setEmail(o.getOne().getOne().getEmail());
				    setContact_No1(o.getOne().getOne().getContact_No1());
				    setContact_No2(o.getOne().getOne().getContact_No2());
				    setCandidateskills(getCandidateSubSkillList(o.getOne().getOne().getCandidateId()));
				    setTestId(o.getOne().getTwo().getTestId());
				    setTestDate(o.getOne().getTwo().getTestDate());
				    setTestCode(o.getOne().getTwo().getTestCode());
				    setIsPassed(o.getTwo().getIsPassed());
				    setMarks(o.getTwo().getMarks());
			 
		 }})
		 .collect(Collectors.toList());
		return new AsyncResult<List<CustomTest>>(evaluatedCandidate);		
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
	

}
