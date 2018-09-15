package com.spark

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.matching.Regex


object wordCount {

  //匹配IP地址
  /**
    *
"(
( ?:( ?:25[0-5] | 2[0-4]\\d | ( (1\\d{2}) | ([1-9]?\\d) ) ) \\. )
{3}
( ?:25[0-5] | 2[0-4]\\d | ((1\\d{2}) | ([1-9]?\\d) ) )
)".r
    */
  val  IPPattern="((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))".r

  //匹配文件名
  val  fileNamePattern="([0-9]+).mp4".r

  //[15/Feb/2017:11:17:13 +0800]  匹配 2017:11 按每小时播放量统计
  val  timePattern=".*(2017):([0-9]{2}):[0-9]{2}:[0-9]{2}.*".r
  //匹配 http 响应码和请求数据大小
  val httpSizePattern=".*\\s(200|206|304)\\s([0-9]+)\\s.*".r


  /**
    * 将行日志中视频文件名与IP地址取出
    * @param line
    * @return
    */
  def getFileNameAndIp(line:String)={
    (fileNamePattern.findFirstIn(line).mkString, IPPattern.findFirstIn(line).mkString)
  }

  /**
    * 正则匹配
    * @param pattern
    * @param str
    * @return
    */
  def  isMatch( pattern : Regex, str : String )={
    str match {
      case pattern( _* ) => true
      case _ => false
    }
  }

  /**
    * 获取日志中小时和http 请求体大小
    * @param line
    * @return
    */
  def getTimeAndSize(line:String)={
    var res=("",0L)
    try{
      val  httpSizePattern( code, size ) = line
      val  timePattern( year, hour ) = line
      res = ( hour, size.toLong )
    }catch {
      case ex:Exception  => ex.printStackTrace()
    }
    res
  }


  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("hello").setMaster("local")
    val sc = new SparkContext(conf)
    //从文件中获取数据
    //val path="D:\\data\\video\\log\\2017-07-21"
    /**
IP 命中率 响应时间 请求时间 请求方法 请求URL    请求协议 状态吗 响应大小 referer 用户代理
ClientIP Hit/Miss ResponseTime [Time Zone] Method URL Protocol StatusCode TrafficSize Referer UserAgent
      */
    val path="/Users/tanjiquan/IdeaProjects/spark/helloWord/src/main/resources/cdn.txt"
    val input = sc.textFile(path)

    println("----------------RDD: " + input.count())

    //1.统计独立IP数
    val ipNums=input.flatMap( x => IPPattern findFirstIn(x) )
                    .map(x=>( x, 1))
                    .reduceByKey( (x, y) => x+y )
                    .sortBy( _._2, false )

    //输出IP访问数前量前10位
    ipNums.take(10).foreach(println)
    println("独立IP数："+ipNums.count())


    //2.统计每个视频独立IP数
    input.filter( x => x.matches(".*([0-9]+)\\.mp4.*") )
         .map( x => getFileNameAndIp(x) )
         .groupByKey()
         .map( x => ( x._1, x._2.toList.distinct ) )
         .sortBy( _._2.size, false )
         .take(10)
         .foreach( x => println( "视频：" + x._1 + " 独立IP数:" + x._2.size ) )


    //3.统计一天中每个小时间的流量
    input.filter( x => isMatch( httpSizePattern, x ) )
         .filter( x => isMatch( timePattern, x ) )
         .map( x => getTimeAndSize(x) )
         .groupByKey()
         .map( x => (x._1, x._2.sum) )
         .sortByKey()
         .foreach( x => println( x._1 + "时 CDN流量=" + x._2/(1024*1024*1024)+"G") )
  }

}
