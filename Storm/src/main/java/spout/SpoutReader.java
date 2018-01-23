package spout;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

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
		 System.out.println("FAIL:"+MsgId);
	}
	//创建一个文件并维持一个collector对象
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		 try {
             this.context = context;
             this.fileReader = new FileReader(conf.get("wordsFile").toString());
         } catch (FileNotFoundException e) {
             throw new RuntimeException("Error reading file ["+conf.get("wordFile")+"]");
         }
         this.collector = collector;
		
	}
	public void nextTuple() {
		// 改方法会被不停的调用，直到文件被读取完成
		if(completed){//用于文件读取完成与否的判断。
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //什么也不做
            }
           return;
        }
        String str;
        //创建reader
        BufferedReader reader = new BufferedReader(fileReader);
        try{
            //读所有文本行
           while((str = reader.readLine()) != null){
            /**
             * 按行发布一个新值
             */
                this.collector.emit(new Values(str),str);
            }
        }catch(Exception e){
            throw new RuntimeException("Error reading tuple",e);
        }finally{
            completed = true;
        }
		
	}
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		  declarer.declare(new Fields("line"));
		
	}
}
