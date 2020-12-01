//
// Created by tck on 2020/12/1.
//

#ifndef MY_OPENSL_RECORDBUFFER_H
#define MY_OPENSL_RECORDBUFFER_H


class RecordBuffer {
public:
    short **buffer;
    int index = -1;
public:
    RecordBuffer(int bufferSize);
    ~RecordBuffer();

public:
    //即将录入PCM数据的buffer
    short *getRecordBuffer();
    //当前录制好的PCM数据的buffer,可以写入文件
    short *getNowBuffer();
};


#endif //MY_OPENSL_RECORDBUFFER_H
