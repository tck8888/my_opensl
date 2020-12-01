//
// Created by tck on 2020/12/1.
//

#include "RecordBuffer.h"

RecordBuffer::RecordBuffer(int bufferSize) {
    buffer = new short *[2];
    for (int i = 0; i < 2; i++) {
        buffer[i] = new short[bufferSize];
    }
}

RecordBuffer::~RecordBuffer() {

}

short *RecordBuffer::getRecordBuffer() {
    this->index++;
    if (index > 1) {
        index = 0;
    }
    return buffer[index];
}

short *RecordBuffer::getNowBuffer() {
    return buffer[index];
}
