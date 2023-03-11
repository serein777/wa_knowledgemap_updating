package edu.hhu.wa_knowledgemap_updating.kettle;

import lombok.extern.slf4j.Slf4j;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.Result;
import org.pentaho.di.core.RowMetaAndData;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class KettleService {

        //ktr、kjb执行文件所在路径
        @Value("${kettle.script.path}")
        private String dirPath;

        /**
         * 执行ktr文件
         * @param filename 文件名
         * @param params 命名参数
         * @param variables 变量
         * @return
         */
        public String runKtr(String filename, Map<String, String> params, Map<String, String> variables) {
            try {
                log.info("开始执行[{}]文件，参数如下：", filename);
                KettleEnvironment.init();
                TransMeta tm = new TransMeta(dirPath +"/" + filename+".ktr");
                Trans trans = new Trans(tm);

                if (params != null) {
                    for(String key: params.keySet()){
                        log.info("参数名：{}, 参数值： {}", key, params.get(key));
                        trans.setParameterValue(key,params.get(key));
                    }
                }
                if (variables != null) {
                    for(String key: variables.keySet()){
                        log.info("变量名：{}, 变量值： {}", key, variables.get(key));
                        trans.setVariable(key,variables.get(key));
                    }
                }
                trans.execute(null);
                trans.waitUntilFinished();
                Result result = trans.getResult();
                List<RowMetaAndData> rows = result.getRows(); //获取数据
                for (RowMetaAndData row : rows) {
                    RowMetaInterface rowMeta = row.getRowMeta(); //获取列的元数据信息
                    String[] fieldNames = rowMeta.getFieldNames();
                    Object[] datas = row.getData();
                    for (int i = 0; i < fieldNames.length; i++) {
                        System.out.println(fieldNames[i]+"="+datas[i]);
                    }
                }
            } catch (Exception e) {
                log.error("执行[{}]报错，错误原因：{}", filename, e.getMessage(), e);
            }
            return "ok";
        }

        /**
         * 执行kjb文件
         * @param filename 文件名
         * @param params 命名参数
         * @param variables 变量
         * @return
         */
        public   Result   runKjb(String filename, Map<String, String> params, Map<String, String> variables) {
            Result result=null;
            try {
                KettleEnvironment.init();
                JobMeta jm = new JobMeta(dirPath + "/" + filename+".kjb", null);
                Job job = new Job(null, jm);
                if (params != null) {
                    for(String key: params.keySet()){
                        log.info("参数名：{}, 参数值： {}", key, params.get(key));
                        job.setParameterValue(key,params.get(key));
                    }
                }
                if (variables != null) {
                    for(String key: variables.keySet()){
                        log.info("变量名：{}, 变量值： {}", key, variables.get(key));
                        job.setVariable(key,variables.get(key));
                    }
                }
                job.start();
                job.waitUntilFinished();
                result = job.getResult();
            } catch (Exception e) {
                log.error("执行[{}]报错，错误原因：{}", filename, e.getMessage(), e);
            }

            return result;
        }

}

