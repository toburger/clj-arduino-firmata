(ns siglato.core
  (:require [firmata.util :as util]
            [serial.core :as serial]))

(comment

  (serial/port-identifiers)

  (util/detect-arduino-port))

;; ; Exception java.lang.UnsatisfiedLinkError: /private/var/folders/jw/v0xfph2n7d1cqbg0hkd7mzym0000gn/T/jna--868451404/jna6106220861697591463.tmp: dlopen(/private/var/folders/jw/v0xfph2n7d1cqbg0hkd7mzym0000gn/T/jna--868451404/jna6106220861697591463.tmp, 0x0001): tried: '/private/var/folders/jw/v0xfph2n7d1cqbg0hkd7mzym0000gn/T/jna--868451404/jna6106220861697591463.tmp' (fat file, but missing compatible architecture (have 'i386,x86_64,unknown', need 'arm64')), '/System/Volumes/Preboot/Cryptexes/OS/private/var/folders/jw/v0xfph2n7d1cqbg0hkd7mzym0000gn/T/jna--868451404/jna6106220861697591463.tmp' (no such file), '/private/var/folders/jw/v0xfph2n7d1cqbg0hkd7mzym0000gn/T/jna--868451404/jna6106220861697591463.tmp' (fat file, but missing compatible architecture (have 'i386,x86_64,unknown', need 'arm64')) [in thread "nREPL-session-44205659-ac8e-409d-a936-c31afc28f9a0"]