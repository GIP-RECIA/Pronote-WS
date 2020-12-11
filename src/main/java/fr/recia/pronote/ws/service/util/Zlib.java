/**
 * Copyright (C) 2020 GIP-RECIA https://www.recia.fr/
 * @Author (C) 2020 Julien Gribonvald <julien.gribonvald@recia.fr>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *                 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.recia.pronote.ws.service.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.DataFormatException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Zlib {
    /**
     * Compresses an array of bytes using Zlib.
     * @param data The array of bytes to compress
     * @return     The compressed bytes
     */
    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DeflaterOutputStream outputStream = new DeflaterOutputStream(baos);
        outputStream.write(data);
        outputStream.finish();

        return baos.toByteArray();
    }

    /**
     * Decompresses a compressed array of bytes.
     * @param data                 The compressed bytes
     * @return                     The decompressed bytes
     * @throws DataFormatException Thrown when the passed bytes are the wrong format
     */
    public static byte[] decompress(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream outputStream = new InflaterOutputStream(baos);
        outputStream.write(data);
        outputStream.finish();

        return baos.toByteArray();
    }

    public static String byteArrayToString(byte[] a) {
        return new String(a, StandardCharsets.UTF_8);
    }

    public static void check(final String toCompress) throws IOException {
        log.info("Original String:   " + toCompress);
        byte[] compressed = Zlib.compress(toCompress.getBytes(StandardCharsets.UTF_8));
        log.info("Compressed String: " + byteArrayToString(compressed));
        log.info("Original: %d%n", toCompress.length());
        log.info("Compress: %d%n", compressed.length);
        byte[] decompressed = Zlib.decompress(compressed);
        log.info("Decompressed Str:  " + byteArrayToString(decompressed));
    }
}
