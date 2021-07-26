package com.demo.project.groovyDSL

/**
 * Created on 2021/7/26.
 * 1.BroadBandPlus:为了展示我们的这个 DSL 是如何工作的，我们必须得构建一些应用的基本骨架来运行我们的 BroadbandPlus 服务。
 * 这里我们也不必太纠结这些骨架类的细节，它们唯一的目的只是 为了提供一个钩子来运行我们的 DSL，并非是一个实际的工作的系统
 * @author xuebaopeng* Description
 */
class BroadbandPlus {
    //后面会说.这个类是我们DSL的核心类
    def rewards = new RewardService()

    def canConsume = { account, media ->
        def now = new Date()
        if (account.mediaList[media]?.after(now))
            return true
        account.points > media.points
    }
    def consume = { account, media ->
        // 第一次消费才奖励
        if (account.mediaList[media.title] == null) {
            def now = new Date()
            account.points -= media.points account.mediaList[media] = now + media.daysAccess // 应用 DSL 奖励规则 rewards.applyRewardsOnConsume(account, media)
        }
    }

    def extend = {account, media, days ->
        if (account.mediaList[media] != null) {
            account.mediaList[media] += days
        }
    }
}
