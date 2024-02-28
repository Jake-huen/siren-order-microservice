sudo su -

swapoff -a && sed -i '/swap/s/^/#/' /etc/fstab

cat <<EOF | sudo tee /etc/modules-load.d/k8s.conf
br_netfilter
EOF
cat <<EOF>>  /etc/sysctl.d/k8s.conf
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF

sysctl --system

172.10.40.174 -> $ hostname k8s-master
172.10.40.43 -> $ hostname k8s-node01
172.10.40.34 -> $ hostname k8s-node02
172.10.40.37 -> $ hostname k8s-node03