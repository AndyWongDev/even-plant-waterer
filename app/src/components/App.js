import React from 'react';
import 'antd/dist/antd.css';
import { Layout, Menu, Breadcrumb } from 'antd';
import PlantCard from './PlantCard';

const { Header, Content, Footer } = Layout;

const plantJson = require("../mockData/getAllPlants.json")

const plantList = plantJson.map(plant => <PlantCard key={plant.id} {...plant} />)

const App = () => (
  <Layout>
    <Header style={{ position: 'fixed', zIndex: 1, width: '100%' }}>
      <div className="logo" />
      <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
        <Menu.Item key="1">Plants</Menu.Item>
        <Menu.Item key="2">Schedule</Menu.Item>
      </Menu>
    </Header>
    <Content
      className="site-layout"
      style={{ padding: '0 50px', marginTop: 64 }}
    >
      <Breadcrumb style={{ margin: '16px 0' }}>
        <Breadcrumb.Item>Home</Breadcrumb.Item>
        <Breadcrumb.Item>Plants</Breadcrumb.Item>
      </Breadcrumb>
      <div
        className="site-layout-background"
        style={{ padding: 24, minHeight: 380 }}
      >
      {plantList}
      </div>
    </Content>
    <Footer style={{ textAlign: 'center' }}>©2020</Footer>
  </Layout>
);

export default App;
