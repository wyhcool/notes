import './App.less';
import React from 'react';
export default class App extends React.Component {
  constructor() {
    super();
    fetch('/api/business/faq/faqTypeList').then(res => {
      if (res.status !== 200) {
        throw new Error(`Fail Response Status: ${res.status}`)
      }
      res.json().then(response => {
        console.log(`Success: ${JSON.stringify(response)}`)
      }).catch(err => {
        console.log(`Res Err: ${JSON.stringify(err)}`)
      })
    }).catch(err => {
      console.log(`Err: ${JSON.stringify(err)}`)
    })
  }

  render() {
    return (
      <div className='app'>
        app Here!
      </div>
    );
  }
}


