package bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordNormalizer implements IRichBolt {
	private OutputCollector collector;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector=collector;

	}

	@Override
	public void execute(Tuple input) {
		String str=input.getString(0);//从元组中拿到数据，并将其转化为字符串。
		String []strings=str.split(" ");
		for(String word :strings) {
			word=word.trim();//去除两边的空格
			if(!word.isEmpty()) {
				word=word.toLowerCase();//将其转化为小写字母
				collector.emit(new Values(word));//发布
			}
		}
		collector.ack(input);//对元组做出回答。
	}

	@Override
	public void cleanup() {
		//该方法在元组处理完成后进行，在处理完成后进行资源的释放

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		//发布word域
		declarer.declare(new Fields("word"));
	}

}
