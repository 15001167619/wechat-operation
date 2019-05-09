package web.page.authorization.template;

/**
 * 预警信息实体
 * {{first.DATA}} 
 * 发布单位：{{alarm_unit.DATA}} 
 * 预警类别：{{alarm_type.DATA}} 
 * 预警级别：{{alarm_level.DATA}} 
 * 发布时间：{{alarm_time.DATA}} 
 * {{remark.DATA}}
 * @Author wuwz
 * @TypeName WarnInfoModel
 */
public class WarnInfoModel {
	
	private String first; //标题
	private String alarm_unit; //发布单位
	private String alarm_type; //预警类别
	private String alarm_level;//预警级别
	private String alarm_time; //发布时间
	private String remark; //内容
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getAlarm_unit() {
		return alarm_unit;
	}
	public void setAlarm_unit(String alarm_unit) {
		this.alarm_unit = alarm_unit;
	}
	public String getAlarm_type() {
		return alarm_type;
	}
	public void setAlarm_type(String alarm_type) {
		this.alarm_type = alarm_type;
	}
	public String getAlarm_level() {
		return alarm_level;
	}
	public void setAlarm_level(String alarm_level) {
		this.alarm_level = alarm_level;
	}
	public String getAlarm_time() {
		return alarm_time;
	}
	public void setAlarm_time(String alarm_time) {
		this.alarm_time = alarm_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
