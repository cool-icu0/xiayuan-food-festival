# 安装支持组件
yum install -y yum-utils device-mapper-persistent-data lvm2
# 安装docker 官方镜像源 给魔法用户 二选一
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

# 安装docker 阿里云镜像源 给物理用户 二选一
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

yum install -y docker-ce

# 启动docker
systemctl start docker

# 设置开机启动
systemctl enable docker