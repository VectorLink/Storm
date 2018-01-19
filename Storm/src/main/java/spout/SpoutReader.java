package spout;

import java.io.FileReader;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;

public class SpoutReader implements IRichSpout{
	//实现IRichSpout接口，获取数据源
	private SpoutOutputCollector collector;//应该是数据源收集器
	private FileReader fileReader;//文件读取
	private boolean completed =false;//文件完成与否
	private TopologyContext context;//连接上下文
	public boolean isDistributed() {return false;}
	public void ack(Object MsgId) {
		System.out.println("ok"+MsgId);
	}
	public void close() {}
	public void fail(Object MsgId) {
		
	}
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		
	}
	public void nextTuple() {
		// TODO Auto-generated method stub
		
	}
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}
}
