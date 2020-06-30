FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " file://wpa_supplicant-wlan0.conf"

FILES_${PN} += "${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf"

do_install_append() {

  install -d ${D}${sysconfdir}/wpa_supplicant


  install -m 0755 ${WORKDIR}/wpa_supplicant-wlan0.conf ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-nl80211-wlan0.conf

  # Make sure the system directory for systemd exists.
  install -d ${D}${sysconfdir}/systemd/system/multi-user.target.wants/

  # Link the service file for autostart.
  ln -s ${systemd_unitdir}/system/wpa_supplicant-nl80211@.service \
  ${D}${sysconfdir}/systemd/system/multi-user.target.wants/wpa_supplicant-nl80211@wlan0.service

}
