package bolt;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class wordcounter implements IRichBolt {
	Integer Id;
	String name;
	Map<String,Integer> counters;
	private OutputCollector collector;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.counters=new HashMap<String, Integer>();
		this.collector=collector;
		this.name=context.getThisComponentId();
		this.Id=context.getThisTaskId();

	}

	@Override
	public void execute(Tuple input) {
		String str=input.getString(0);
		if(!counters.containsKey(str)) {
			counters.put(str, 1);
		}else {
			Integer c=counters.get(str)+1;
			counters.put(str, c);
		}
		collector.ack(input);

	}

	@Override
	public void cleanup() {
		 System.out.println("-- 单词数 【"+name+"-"+Id+"】 --");
	        for(Map.Entry<String,Integer> entry : counters.entrySet()){
	            System.out.println(entry.getKey()+": "+entry.getValue());
	        }

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		

	}

}