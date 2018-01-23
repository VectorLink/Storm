package topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import bolt.WordNormalizer;
import bolt.wordcounter;
import spout.SpoutReader;

public class wordTopology {
	public static void main(String[] args) throws Exception {
		//定义拓扑
		TopologyBuilder builder=new TopologyBuilder();
		//设置输入流
		builder.setSpout("word-reader", new SpoutReader());
		//设置处理流
		builder.setBolt("word-normalizer", new WordNormalizer()).
		shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new wordcounter()).
		fieldsGrouping("word-normalizer", new Fields("word"));
		//配置文件
		Config conf =new Config();
		conf.put("wordsFile", args[0]);
		conf.setDebug(false);
		//运行拓扑
		conf.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Getting-Started-Topologie", conf, builder.createTopology());
        Thread.sleep(1000);
        cluster.shutdown();
	}
}
