import React, { useEffect, useState } from 'react';
import { Layout, Menu, Breadcrumb } from 'antd';
import PlantCard from './PlantCard';
import 'antd/dist/antd.css';

const { Header, Content, Footer } = Layout;

const getPlantList = (plantJson) =>
  plantJson.map((plant) => <PlantCard key={plant.id} {...plant} />);

const App = () => {
  const [menuItem, setMenuItem] = useState('Plants');
  const [plants, setPlants] = useState([]);

  useEffect(() => {
    fetch(`/plants`)
      .then((res) => res.json())
      .then((res) => setPlants(res));
  });

  return (
    <Layout>
      <Header style={{ position: 'fixed', zIndex: 1, width: '100%' }}>
        <div className="logo" />
        <Menu theme="dark" mode="horizontal" defaultSelectedKeys={[menuItem]}>
          <Menu.Item key="Plants" onClick={() => setMenuItem('Plants')}>
            Plants
          </Menu.Item>
          <Menu.Item key="Schedule" onClick={() => setMenuItem('Schedule')}>
            Schedule
          </Menu.Item>
        </Menu>
      </Header>
      <Content
        className="site-layout"
        style={{ padding: '0 50px', marginTop: 64 }}
      >
        <Breadcrumb style={{ margin: '16px 0' }}>
          <Breadcrumb.Item>Home</Breadcrumb.Item>
          <Breadcrumb.Item>{menuItem}</Breadcrumb.Item>
        </Breadcrumb>
        <div
          className="site-layout-background"
          style={{ padding: 24, minHeight: 380 }}
        >
          {menuItem === 'Plants' ? getPlantList(plants) : null}
        </div>
      </Content>
      <Footer style={{ textAlign: 'center' }}>©2020</Footer>
    </Layout>
  );
};

export default App;
