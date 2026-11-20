package com.scholarshipmatcher.service;
import com.scholarshipmatcher.entity.*; import com.scholarshipmatcher.repository.ScholarshipRepository; import org.springframework.stereotype.Service; import java.util.*;
@Service public class MatchingService {
 private final ScholarshipRepository repo; public MatchingService(ScholarshipRepository repo){this.repo=repo;}
 public Map<String,Object> match(Map<String,Object> p){
  if(p==null)p=Map.of();
  var out=new ArrayList<Map<String,Object>>();int eligible=0,review=0,not=0;
  for(var s:repo.findAll()){
   var c=new ArrayList<Map<String,String>>();
   check(c,"State",s.state,value(p,"state"));check(c,"Category",s.category,value(p,"category"));check(c,"Gender",s.gender,value(p,"gender"));
   check(c,"Education level",s.educationLevel,value(p,"level","educationLevel"));check(c,"Course",s.course,value(p,"course"));
   check(c,"Academic year",s.academicYear,value(p,"year","academicYear"));number(c,"Academic marks",s.minMarks,value(p,"marks"),true);
   number(c,"Annual family income",s.maxIncome,value(p,"income","familyIncome"),false);
   long fails=c.stream().filter(x->x.get("status").equals("fail")).count(),missing=c.stream().filter(x->x.get("status").equals("unknown")).count();
   String status=fails>0?"not":(missing>0||c.isEmpty())?"review":"eligible";if(status.equals("eligible"))eligible++;else if(status.equals("review"))review++;else not++;
   var item=new LinkedHashMap<String,Object>();item.put("scholarship",s);item.put("status",status);item.put("conditions",c);
   item.put("reason",fails>0?"One or more mandatory conditions are not met.":c.isEmpty()?"Eligibility criteria have not been configured.":missing>0?"No known condition failed, but information is missing.":"All listed conditions are satisfied. This is not official approval.");out.add(item);
  }
  var counts=Map.of("potentialMatches",eligible,"needsReview",review,"notEligible",not);
  return Map.of("results",out,"counts",counts,"potentialMatches",eligible,"needsReview",review,"notEligible",not,"disclaimer","Preliminary matching only. The scholarship authority makes final decisions.");
 }
 private Object value(Map<String,Object> profile,String key,String... aliases){Object result=profile.get(key);if(result!=null)return result;for(String alias:aliases){result=profile.get(alias);if(result!=null)return result;}return null;}
 private void check(List<Map<String,String>> out,String label,String required,Object actual){if(required==null||required.isBlank())return;boolean matches=actual!=null&&Arrays.stream(required.split(",")).map(String::trim).anyMatch(x->label.equals("Course")?actual.toString().toLowerCase().contains(x.toLowerCase()):x.equalsIgnoreCase(actual.toString()));String st=actual==null||actual.toString().isBlank()?"unknown":matches?"pass":"fail";add(out,label,st,st.equals("unknown")?"Information missing":st.equals("pass")?"Requirement satisfied":"Required: "+required+"; provided: "+actual);}
 private void number(List<Map<String,String>> out,String label,Number limit,Object actual,boolean min){if(limit==null)return;String st="unknown",detail="Information missing";try{if(actual!=null&&!actual.toString().isBlank()){double n=Double.parseDouble(actual.toString());boolean ok=min?n>=limit.doubleValue():n<=limit.doubleValue();st=ok?"pass":"fail";detail=ok?"Requirement satisfied":(min?"Minimum ":"Maximum ")+limit;}}catch(Exception e){st="unknown";detail="Invalid or missing value";}add(out,label,st,detail);}
 private void add(List<Map<String,String>> a,String l,String s,String d){a.add(Map.of("condition",l,"status",s,"detail",d));}
}
