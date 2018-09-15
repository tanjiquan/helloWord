package com.spark
import org.apache.spark.{SparkConf, SparkContext}

//https://blog.csdn.net/whzhaochao/article/details/72358215

object helloSpark {
  def main(args: Array[String]): Unit = {
    //设置本机Spark配置
    val conf = new SparkConf().setAppName("wordCount").setMaster("local")
    //创建Spark上下
    val sc = new SparkContext(conf)
    //从文件中获取数据
    val input = sc.textFile("/Users/tanjiquan/Desktop/WordCount.scala")
    //分析并排序输出统计结果
    input.flatMap(line => line.split(" "))
      .map(word => (word, 1))
      .reduceByKey((x, y) => x + y)
      .sortBy( _._2, false )
      .foreach(println _)

    /**
计算过程分析
- flatMap(line => line.split(” “)) 按安空格拆分文件中单词
- map(word => (word, 1)) 将每个词映射成 (word,1)，word是重复的
- reduceByKey((x, y) => x + y) 将key相同的单词相加得到，word不重复
- sortBy(_._2,false) 按词数量排序
- foreach(println _) 输出结题
      */
    Thread.sleep( 1000000);
  }

}