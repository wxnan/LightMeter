package com.example.lightmeter.data

object DataRepository {
    
    val scenes = listOf(
        SceneType(
            id = "reading",
            name = "阅读区",
            recommendedLux = "300-750",
            colorTemp = "4000-5000K",
            description = "需要充足且均匀的照明，避免产生阴影和眩光",
            tips = listOf(
                "使用可调节台灯，光源位于书本前上方",
                "主照明500 lux以上",
                "背景照明不低于150 lux",
                "建议使用无频闪灯具"
            ),
            specialRequirements = listOf(
                "显色指数 Ra>90",
                "主照明区域建议低于1500 lux",
                "避免与背景照明亮度差异过大",
                "避免蓝光过强"
            )
        ),
        SceneType(
            id = "living",
            name = "客厅",
            recommendedLux = "100-300",
            colorTemp = "3000-4000K",
            description = "多功能空间，需要灵活的照明方案",
            tips = listOf(
                "主照明150-300 lux",
                "重点照明区域300 lux以上",
                "使用多层次照明设计",
                "可调节亮度满足不同需求"
            ),
            specialRequirements = listOf(
                "均匀分布光源",
                "色温不宜高于5000K",
                "避免眩光",
                "装饰照明营造氛围"
            )
        ),
        SceneType(
            id = "bedroom",
            name = "卧室",
            recommendedLux = "75-300",
            colorTemp = "3000-4000K",
            description = "营造温馨舒适的氛围，有利于放松和睡眠",
            tips = listOf(
                "主照明150 lux以上",
                "工作阅读区域需达到300 lux以上",
                "使用暖色温灯光",
                "可调节亮度满足阅读、休息等不同需求"
            ),
            specialRequirements = listOf(
                "避免强光刺激",
                "可使用间接照明",
                "夜灯10-30 lux"
            )
        ),
        SceneType(
            id = "dining",
            name = "餐厅",
            recommendedLux = "150-300",
            colorTemp = "3000-4000K",
            description = "营造温馨就餐氛围，增强食欲",
            tips = listOf(
                "餐桌上方200-300 lux",
                "环境照明150 lux以上",
                "使用暖色温光源",
                "吊灯距离桌面70-80cm"
            ),
            specialRequirements = listOf(
                "显色指数 Ra>85",
                "集中式照明",
                "可调光设计"
            )
        ),
        SceneType(
            id = "kitchen",
            name = "厨房",
            recommendedLux = "100-300",
            colorTemp = "3500-5000K",
            description = "工作区需要明亮清晰的照明，确保操作安全",
            tips = listOf(
                "操作台面300 lux以上",
                "整体照明200 lux",
                "使用防水防油灯具",
                "冷白光提高辨识度"
            ),
            specialRequirements = listOf(
                "显色指数 Ra>90",
                "无阴影照明",
                "易清洁灯具"
            )
        ),
        SceneType(
            id = "bathroom",
            name = "卫生间",
            recommendedLux = "100-300",
            colorTemp = "3000-5000K",
            description = "镜前区域需要充足照明，整体明亮清洁",
            tips = listOf(
                "镜前照明300 lux以上",
                "整体照明150 lux以上",
                "使用防水灯具",
                "避免产生阴影"
            ),
            specialRequirements = listOf(
                "防水等级IP44以上",
                "镜前无阴影照明",
                "镜前显色指数 Ra>90"
            )
        ),
        SceneType(
            id = "study",
            name = "书房",
            recommendedLux = "300-500",
            colorTemp = "3500-5000K",
            description = "长时间用眼场所，需要优质照明环境",
            tips = listOf(
                "桌面阅读区500 lux以上",
                "整体照明300 lux以上",
                "台灯+顶灯组合照明",
                "避免明暗对比过大"
            ),
            specialRequirements = listOf(
                "无频闪",
                "可调节色温",
                "减少蓝光"
            )
        ),
        SceneType(
            id = "corridor",
            name = "走廊",
            recommendedLux = "100-150",
            colorTemp = "3000-4000K",
            description = "保证基本通行安全，节能为主",
            tips = listOf(
                "基础照明100 lux",
                "可使用感应灯",
                "均匀分布光源",
                "避免眩光"
            ),
            specialRequirements = listOf(
                "感应控制节能",
                "柔和均匀",
                "应急照明"
            )
        ),
        SceneType(
            id = "children",
            name = "儿童房",
            recommendedLux = "200-300",
            colorTemp = "3000-5000K",
            description = "保护儿童视力，营造活泼明亮的环境",
            tips = listOf(
                "学习区域500-750 lux",
                "整体照明200 lux以上",
                "睡眠区域可调至30 lux",
                "避免频闪和眩光"
            ),
            specialRequirements = listOf(
                "无频闪必备",
                "显色指数 Ra>90",
                "可调光调色温",
                "安全防护设计"
            )
        ),
        SceneType(
            id = "elderly",
            name = "老人房",
            recommendedLux = "200-500",
            colorTemp = "3000-5000K",
            description = "提高照度补偿视力下降，注重安全舒适",
            tips = listOf(
                "整体照度提高30-50%",
                "床头阅读灯500 lux",
                "夜间照明50 lux",
                "避免强烈明暗对比"
            ),
            specialRequirements = listOf(
                "显色指数 Ra>85",
                "无眩光设计",
                "易操作开关",
                "夜灯常亮"
            )
        )
    )
    
    val plants = listOf(
        Plant(
            id = "pothos",
            name = "绿萝",
            nameEn = "Pothos",
            minLux = 3000,
            maxLux = 8000,
            minPPFD = 50,
            maxPPFD = 100,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("叶片变小", "茎节拉长", "叶色变淡", "生长缓慢"),
            excessSymptoms = listOf("叶片发黄", "叶缘焦枯", "出现晒斑"),
            tips = "光照：耐阴性强，避免强光直射，散射光即可。\n温度：生长适温20~30℃，稍耐寒，5℃以上可安全越冬。"
        ),
        Plant(
            id = "monstera",
            name = "龟背竹",
            nameEn = "Monstera",
            minLux = 5000,
            maxLux = 12000,
            minPPFD = 80,
            maxPPFD = 150,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("叶片不开裂", "叶色暗淡", "茎秆徒长"),
            excessSymptoms = listOf("叶片灼伤", "叶缘干枯", "生长停滞"),
            tips = "光照：喜明亮散射光，叶片开裂需要充足光照。\n温度：生长适温20~30℃，不耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "sansevieria",
            name = "虎尾兰",
            nameEn = "Snake Plant",
            minLux = 2500,
            maxLux = 10000,
            minPPFD = 50,
            maxPPFD = 150,
            hoursPerDay = "2-4",
            lackSymptoms = listOf("叶片软弱", "斑纹不明显", "生长极慢"),
            excessSymptoms = listOf("叶片发白", "叶尖焦枯"),
            tips = "光照：极耐阴，光照范围广，室内任意位置都可生长。\n温度：生长适温18~30℃，耐寒性强，5℃以上可安全越冬。"
        ),
        Plant(
            id = "succulent",
            name = "多肉植物",
            nameEn = "Succulent",
            minLux = 5000,
            maxLux = 30000,
            minPPFD = 80,
            maxPPFD = 300,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("徒长", "叶片稀疏", "颜色变绿", "茎部拉长"),
            excessSymptoms = listOf("叶片晒伤", "出现黑斑", "叶片干瘪"),
            tips = "光照：喜强光，夏季正午需适当遮阴，秋冬多晒可上色。\n温度：生长适温15~28℃，耐寒性差，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "peace_lily",
            name = "白掌",
            nameEn = "Peace Lily",
            minLux = 5000,
            maxLux = 10000,
            minPPFD = 80,
            maxPPFD = 150,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("不开花", "叶片下垂", "生长缓慢"),
            excessSymptoms = listOf("叶片发黄", "叶尖焦枯", "花苞变褐"),
            tips = "光照：耐阴但需适量光照才能开花，避免强光直射。\n温度：生长适温18~28℃，不耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "ficus",
            name = "橡皮树",
            nameEn = "Rubber Plant",
            minLux = 8000,
            maxLux = 20000,
            minPPFD = 100,
            maxPPFD = 250,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("掉叶", "叶片变小", "茎秆细弱"),
            excessSymptoms = listOf("叶片晒伤", "叶色褪色"),
            tips = "光照：喜光，但夏季避免暴晒，定期转盆保证受光均匀。\n温度：生长适温20~30℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "spider_plant",
            name = "吊兰",
            nameEn = "Spider Plant",
            minLux = 5000,
            maxLux = 10000,
            minPPFD = 80,
            maxPPFD = 150,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("叶尖干枯", "叶片发黄", "不长小苗"),
            excessSymptoms = listOf("叶片灼伤", "生长停滞"),
            tips = "光照：喜半阴环境，明亮散射光最佳。\n温度：生长适温15~25℃，耐寒性强，5℃以上可安全越冬。"
        ),
        Plant(
            id = "cactus",
            name = "仙人掌",
            nameEn = "Cactus",
            minLux = 15000,
            maxLux = 30000,
            minPPFD = 200,
            maxPPFD = 400,
            hoursPerDay = "8-10",
            lackSymptoms = listOf("徒长", "变形", "颜色暗淡", "不开花"),
            excessSymptoms = listOf("晒伤（罕见）", "发红变紫"),
            tips = "光照：需要强光和长日照，可接受直射光。\n温度：生长适温15~35℃，耐寒性强，冬季可耐0℃低温。"
        ),
        Plant(
            id = "aloe",
            name = "芦荟",
            nameEn = "Aloe Vera",
            minLux = 8000,
            maxLux = 20000,
            minPPFD = 100,
            maxPPFD = 250,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("叶片细长", "颜色变淡", "茎部徒长"),
            excessSymptoms = listOf("叶片发红", "干瘪", "晒斑"),
            tips = "光照：喜光但需适应，刚买回来要逐渐增加光照。\n温度：生长适温15~28℃，稍耐寒，冬季需保持在5℃以上。"
        ),
        Plant(
            id = "zz_plant",
            name = "金钱树",
            nameEn = "ZZ Plant",
            minLux = 5000,
            maxLux = 15000,
            minPPFD = 80,
            maxPPFD = 200,
            hoursPerDay = "3-5",
            lackSymptoms = listOf("新叶细弱", "叶片稀疏", "生长缓慢"),
            excessSymptoms = listOf("叶片发黄", "灼伤斑点"),
            tips = "光照：喜光且极耐阴，散射光或人工照明都可以。\n温度：生长适温18~30℃，耐寒性一般，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "ivy",
            name = "常春藤",
            nameEn = "English Ivy",
            minLux = 2500,
            maxLux = 15000,
            minPPFD = 50,
            maxPPFD = 200,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("叶片变小", "藤蔓徒长", "斑纹消失"),
            excessSymptoms = listOf("叶片焦枯", "晒伤"),
            tips = "光照：耐阴，明亮散射光下斑纹更明显。\n温度：生长适温15~25℃，耐寒性强，-5℃以上可安全越冬。"
        ),
        Plant(
            id = "boston_fern",
            name = "波斯顿蕨",
            nameEn = "Boston Fern",
            minLux = 3000,
            maxLux = 8000,
            minPPFD = 50,
            maxPPFD = 100,
            hoursPerDay = "3-5",
            lackSymptoms = listOf("叶片发黄", "生长停滞", "叶片稀疏"),
            excessSymptoms = listOf("叶尖焦枯", "叶片枯萎"),
            tips = "光照：喜阴湿环境，避免强光和干燥。\n温度：生长适温18~25℃，不耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "philodendron",
            name = "心叶蔓绿绒",
            nameEn = "Philodendron",
            minLux = 2000,
            maxLux = 10000,
            minPPFD = 50,
            maxPPFD = 150,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("茎节拉长", "叶片变小", "攀爬性减弱"),
            excessSymptoms = listOf("叶片发黄", "晒斑"),
            tips = "光照：喜明亮散射光，类似绿萝的养护。\n温度：生长适温18~28℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "dracaena",
            name = "龙血树",
            nameEn = "Dracaena",
            minLux = 3000,
            maxLux = 10000,
            minPPFD = 100,
            maxPPFD = 150,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("叶片下垂", "新叶细弱", "生长缓慢"),
            excessSymptoms = listOf("叶尖焦枯", "叶色褪色"),
            tips = "光照：耐半阴，但充足光照生长更好。\n温度：生长适温20~30℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "bamboo_palm",
            name = "散尾葵",
            nameEn = "Bamboo Palm",
            minLux = 5000,
            maxLux = 10000,
            minPPFD = 100,
            maxPPFD = 150,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("叶色暗淡", "生长缓慢", "叶片稀疏"),
            excessSymptoms = listOf("叶片焦枯", "叶尖干枯"),
            tips = "光照：喜明亮散射光，避免强光直射。\n温度：生长适温18~28℃，不耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "calathea",
            name = "竹芋",
            nameEn = "Calathea",
            minLux = 1000,
            maxLux = 2500,
            minPPFD = 50,
            maxPPFD = 80,
            hoursPerDay = "3-5",
            lackSymptoms = listOf("斑纹不明显", "叶片下垂", "卷叶"),
            excessSymptoms = listOf("叶片焦边", "褪色", "晒斑"),
            tips = "光照：喜阴，强光会损害叶片，需要高湿度。\n温度：生长适温18~28℃，不耐寒，冬季需保持在18℃以上。"
        ),
        Plant(
            id = "anthurium",
            name = "红掌",
            nameEn = "Anthurium",
            minLux = 5000,
            maxLux = 10000,
            minPPFD = 100,
            maxPPFD = 180,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("不开花", "花色暗淡", "生长缓慢"),
            excessSymptoms = listOf("叶片晒伤", "花苞变褐"),
            tips = "光照：需要明亮散射光才能开花，避免直射。\n温度：生长适温20~30℃，不耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "jade_plant",
            name = "玉树",
            nameEn = "Jade Plant",
            minLux = 15000,
            maxLux = 25000,
            minPPFD = 200,
            maxPPFD = 300,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("徒长", "叶片稀疏", "茎部细弱"),
            excessSymptoms = listOf("叶片发红", "轻微晒伤"),
            tips = "光照：喜光，充足光照下叶缘会变红。\n温度：生长适温15~28℃，耐寒性强，5℃以上可安全越冬。"
        ),
        Plant(
            id = "chinese_money",
            name = "铜钱草",
            nameEn = "Chinese Money Plant",
            minLux = 10000,
            maxLux = 20000,
            minPPFD = 200,
            maxPPFD = 250,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("茎秆细弱", "叶片变小", "倒伏"),
            excessSymptoms = listOf("叶片焦枯", "生长停滞"),
            tips = "光照：喜光喜水，光照充足长势更好。\n温度：生长适温15~28℃，不耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "string_of_pearls",
            name = "珍珠吊兰",
            nameEn = "String of Pearls",
            minLux = 10000,
            maxLux = 20000,
            minPPFD = 200,
            maxPPFD = 250,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("珠体拉长", "变形", "颜色变淡"),
            excessSymptoms = listOf("珠体干瘪", "晒伤"),
            tips = "光照：喜明亮散射光，夏季避免暴晒。\n温度：生长适温18~28℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "schefflera",
            name = "鸭脚木",
            nameEn = "Umbrella Tree",
            minLux = 6000,
            maxLux = 10000,
            minPPFD = 120,
            maxPPFD = 200,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("叶片稀疏", "茎秆徒长", "掉叶"),
            excessSymptoms = listOf("叶片晒伤", "叶色褪色"),
            tips = "光照：喜明亮光线，耐半阴，生长快速。\n温度：生长适温15~30℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "asparagus_fern",
            name = "文竹",
            nameEn = "Asparagus Fern",
            minLux = 800,
            maxLux = 2000,
            minPPFD = 50,
            maxPPFD = 80,
            hoursPerDay = "3-5",
            lackSymptoms = listOf("叶片发黄", "生长缓慢", "枝条稀疏"),
            excessSymptoms = listOf("叶片焦枯", "枯黄脱落"),
            tips = "光照：喜半阴，避免强光直射和干燥。\n温度：生长适温15~25℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "lucky_bamboo",
            name = "富贵竹",
            nameEn = "Lucky Bamboo",
            minLux = 1000,
            maxLux = 3000,
            minPPFD = 60,
            maxPPFD = 100,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("叶片发黄", "生长停滞", "茎秆变软"),
            excessSymptoms = listOf("叶片焦枯", "晒斑"),
            tips = "光照：喜散射光，可水培或土培，避免强光。\n温度：生长适温18~28℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "money_tree",
            name = "发财树",
            nameEn = "Money Tree",
            minLux = 5000,
            maxLux = 10000,
            minPPFD = 80,
            maxPPFD = 150,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("叶片下垂", "茎秆徒长", "掉叶"),
            excessSymptoms = listOf("叶片晒伤", "叶色褪色"),
            tips = "光照：喜明亮散射光，耐半阴，避免暴晒。\n温度：生长适温20~30℃，稍耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "clivia",
            name = "君子兰",
            nameEn = "Clivia",
            minLux = 10000,
            maxLux = 20000,
            minPPFD = 150,
            maxPPFD = 250,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("不开花", "叶片细弱", "生长缓慢"),
            excessSymptoms = listOf("叶片晒伤", "叶尖焦枯"),
            tips = "光照：喜半阴，开花需要光照但避免强光。\n温度：生长适温15~25℃，耐寒性强，5℃以上可安全越冬。"
        ),
        Plant(
            id = "aglaonema",
            name = "万年青",
            nameEn = "Chinese Evergreen",
            minLux = 3000,
            maxLux = 8000,
            minPPFD = 60,
            maxPPFD = 120,
            hoursPerDay = "3-5",
            lackSymptoms = listOf("斑纹消失", "叶色暗淡", "生长缓慢"),
            excessSymptoms = listOf("叶片晒伤", "褪色"),
            tips = "光照：极耐阴，低光环境也能生长良好。\n温度：生长适温18~30℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "kalanchoe",
            name = "长寿花",
            nameEn = "Kalanchoe",
            minLux = 10000,
            maxLux = 20000,
            minPPFD = 150,
            maxPPFD = 250,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("不开花", "徒长", "叶片稀疏"),
            excessSymptoms = listOf("叶片晒伤", "干瘪"),
            tips = "光照：喜光，充足光照才能开花，短日照植物。\n温度：生长适温15~25℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "dracaena_fragrans",
            name = "巴西木",
            nameEn = "Corn Plant",
            minLux = 6000,
            maxLux = 15000,
            minPPFD = 100,
            maxPPFD = 200,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("叶片下垂", "新叶细弱", "生长缓慢"),
            excessSymptoms = listOf("叶尖焦枯", "叶片晒伤"),
            tips = "光照：耐半阴，明亮散射光生长最好。\n温度：生长适温20~30℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "chlorophytum",
            name = "金边吊兰",
            nameEn = "Variegated Spider Plant",
            minLux = 5000,
            maxLux = 10000,
            minPPFD = 80,
            maxPPFD = 120,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("金边消失", "叶片发黄", "不长小苗"),
            excessSymptoms = listOf("叶片灼伤", "金边焦枯"),
            tips = "光照：喜半阴，光照充足金边更明显。\n温度：生长适温15~25℃，耐寒性强，5℃以上可安全越冬。"
        ),
        Plant(
            id = "tradescantia",
            name = "紫竹梅",
            nameEn = "Purple Heart",
            minLux = 15000,
            maxLux = 25000,
            minPPFD = 200,
            maxPPFD = 300,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("紫色变绿", "徒长", "叶色暗淡"),
            excessSymptoms = listOf("叶片焦枯", "晒伤"),
            tips = "光照：光照充足颜色更紫，耐半阴但会褪色。\n温度：生长适温18~28℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "peperomia",
            name = "椒草",
            nameEn = "Peperomia",
            minLux = 1000,
            maxLux = 3000,
            minPPFD = 30,
            maxPPFD = 80,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("叶片变小", "徒长", "斑纹不明显"),
            excessSymptoms = listOf("叶片晒伤", "褪色"),
            tips = "光照：喜明亮散射光，避免强光和积水。\n温度：生长适温18~28℃，稍耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "hoya",
            name = "球兰",
            nameEn = "Hoya",
            minLux = 8000,
            maxLux = 15000,
            minPPFD = 100,
            maxPPFD = 200,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("不开花", "生长缓慢", "叶片稀疏"),
            excessSymptoms = listOf("叶片晒伤", "褪色"),
            tips = "光照：需要明亮光照才能开花，耐旱。\n温度：生长适温18~30℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "fittonia",
            name = "网纹草",
            nameEn = "Nerve Plant",
            minLux = 2000,
            maxLux = 5000,
            minPPFD = 50,
            maxPPFD = 100,
            hoursPerDay = "3-5",
            lackSymptoms = listOf("网纹不明显", "叶色暗淡", "生长缓慢"),
            excessSymptoms = listOf("叶片焦边", "褪色"),
            tips = "光照：喜阴湿，避免强光和干燥。\n温度：生长适温18~28℃，不耐寒，冬季需保持在18℃以上。"
        ),
        Plant(
            id = "maranta",
            name = "孔雀竹芋",
            nameEn = "Prayer Plant",
            minLux = 2500,
            maxLux = 8000,
            minPPFD = 50,
            maxPPFD = 100,
            hoursPerDay = "3-5",
            lackSymptoms = listOf("斑纹消失", "叶片卷曲", "生长缓慢"),
            excessSymptoms = listOf("叶片焦边", "褪色"),
            tips = "光照：喜阴，需要高湿度，叶片会昼开夜合。\n温度：生长适温18~28℃，不耐寒，冬季需保持在18℃以上。"
        ),
        Plant(
            id = "begonia",
            name = "秋海棠",
            nameEn = "Begonia",
            minLux = 5000,
            maxLux = 15000,
            minPPFD = 100,
            maxPPFD = 200,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("不开花", "茎秆徒长", "叶色暗淡"),
            excessSymptoms = listOf("叶片晒伤", "花朵凋谢快"),
            tips = "光照：喜明亮散射光，开花需要充足光照。\n温度：生长适温18~25℃，稍耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "cyclamen",
            name = "仙客来",
            nameEn = "Cyclamen",
            minLux = 8000,
            maxLux = 20000,
            minPPFD = 150,
            maxPPFD = 250,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("不开花", "叶片稀疏", "茎秆细弱"),
            excessSymptoms = listOf("叶片晒伤", "花朵褪色"),
            tips = "光照：喜凉爽明亮环境，开花需要充足光照。\n温度：生长适温12~20℃，不耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "african_violet",
            name = "非洲堇",
            nameEn = "African Violet",
            minLux = 3000,
            maxLux = 10000,
            minPPFD = 80,
            maxPPFD = 120,
            hoursPerDay = "10-12",
            lackSymptoms = listOf("不开花", "叶片徒长", "花色暗淡"),
            excessSymptoms = listOf("叶片晒伤", "褪色"),
            tips = "光照：需要长时间明亮散射光，避免叶片沾水。\n温度：生长适温18~25℃，不耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "bromeliad",
            name = "凤梨",
            nameEn = "Bromeliad",
            minLux = 6000,
            maxLux = 18000,
            minPPFD = 100,
            maxPPFD = 200,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("不开花", "叶色暗淡", "生长缓慢"),
            excessSymptoms = listOf("叶片晒伤", "褪色"),
            tips = "光照：喜明亮散射光，花期长，易养护。\n温度：生长适温18~30℃，稍耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "dieffenbachia",
            name = "花叶万年青",
            nameEn = "Dumb Cane",
            minLux = 5000,
            maxLux = 12000,
            minPPFD = 100,
            maxPPFD = 150,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("斑纹消失", "叶片下垂", "茎秆徒长"),
            excessSymptoms = listOf("叶片晒伤", "褪色"),
            tips = "光照：耐阴，斑纹需要适当光照，有毒需注意。\n温度：生长适温18~30℃，稍耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "croton",
            name = "变叶木",
            nameEn = "Croton",
            minLux = 15000,
            maxLux = 25000,
            minPPFD = 200,
            maxPPFD = 300,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("叶色变绿", "斑纹消失", "掉叶"),
            excessSymptoms = listOf("叶片晒伤", "褪色"),
            tips = "光照：需要充足光照保持叶色，喜高温高湿。\n温度：生长适温20~30℃，不耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "gardenia",
            name = "栀子花",
            nameEn = "Gardenia",
            minLux = 15000,
            maxLux = 25000,
            minPPFD = 200,
            maxPPFD = 300,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("不开花", "叶片发黄", "花苞掉落"),
            excessSymptoms = listOf("叶片晒伤", "花朵褪色快"),
            tips = "光照：喜光照，开花需要充足光照，喜酸性土。\n温度：生长适温18~28℃，稍耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "jasmine",
            name = "茉莉花",
            nameEn = "Jasmine",
            minLux = 20000,
            maxLux = 30000,
            minPPFD = 250,
            maxPPFD = 350,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("不开花", "枝条徒长", "香气减弱"),
            excessSymptoms = listOf("叶片晒伤", "花朵凋谢快"),
            tips = "光照：强光植物，充足光照开花多香气浓。\n温度：生长适温20~30℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "azalea",
            name = "杜鹃",
            nameEn = "Azalea",
            minLux = 10000,
            maxLux = 20000,
            minPPFD = 150,
            maxPPFD = 250,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("不开花", "叶片稀疏", "生长缓慢"),
            excessSymptoms = listOf("叶片晒伤", "花朵凋谢快"),
            tips = "光照：喜明亮散射光，开花需要充足光照，喜酸性。\n温度：生长适温15~25℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "orchid",
            name = "蝴蝶兰",
            nameEn = "Phalaenopsis",
            minLux = 4000,
            maxLux = 12000,
            minPPFD = 80,
            maxPPFD = 150,
            hoursPerDay = "10-12",
            lackSymptoms = listOf("不开花", "叶片软弱", "生长缓慢"),
            excessSymptoms = listOf("叶片晒伤", "褪色"),
            tips = "光照：需要长时间散射光，避免强光，花期长。\n温度：生长适温18~28℃，不耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "yucca",
            name = "剑麻",
            nameEn = "Yucca",
            minLux = 15000,
            maxLux = 25000,
            minPPFD = 200,
            maxPPFD = 300,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("叶片软弱", "生长缓慢", "叶色暗淡"),
            excessSymptoms = listOf("叶尖焦枯", "晒伤（罕见）"),
            tips = "光照：喜强光，耐旱耐晒，生长强健。\n温度：生长适温15~35℃，耐寒性强，-5℃以上可安全越冬。"
        ),
        Plant(
            id = "nephrolepis",
            name = "肾蕨",
            nameEn = "Sword Fern",
            minLux = 3000,
            maxLux = 8000,
            minPPFD = 50,
            maxPPFD = 100,
            hoursPerDay = "3-5",
            lackSymptoms = listOf("叶片发黄", "生长停滞", "叶片稀疏"),
            excessSymptoms = listOf("叶片焦枯", "枯萎"),
            tips = "光照：喜阴湿，避免强光和干燥环境。\n温度：生长适温18~25℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "scindapsus",
            name = "银星绿萝",
            nameEn = "Satin Pothos",
            minLux = 5000,
            maxLux = 12000,
            minPPFD = 80,
            maxPPFD = 150,
            hoursPerDay = "4-6",
            lackSymptoms = listOf("银斑消失", "叶片变小", "徒长"),
            excessSymptoms = listOf("叶片晒伤", "银斑褪色"),
            tips = "光照：耐阴，光照充足银斑更明显。\n温度：生长适温18~28℃，稍耐寒，冬季需保持在10℃以上。"
        ),
        Plant(
            id = "alocasia",
            name = "观音莲",
            nameEn = "Elephant Ear",
            minLux = 5000,
            maxLux = 15000,
            minPPFD = 100,
            maxPPFD = 200,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("叶片下垂", "叶色暗淡", "生长缓慢"),
            excessSymptoms = listOf("叶片晒伤", "焦边"),
            tips = "光照：喜明亮散射光，需要高湿度。\n温度：生长适温20~30℃，稍耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "columnea",
            name = "金鱼吊兰",
            nameEn = "Goldfish Plant",
            minLux = 5000,
            maxLux = 10000,
            minPPFD = 80,
            maxPPFD = 120,
            hoursPerDay = "5-7",
            lackSymptoms = listOf("不开花", "枝条徒长", "叶片稀疏"),
            excessSymptoms = listOf("叶片晒伤", "花朵凋谢快"),
            tips = "光照：需要明亮散射光才能开花，喜温暖。\n温度：生长适温18~28℃，稍耐寒，冬季需保持在15℃以上。"
        ),
        Plant(
            id = "mint",
            name = "薄荷",
            nameEn = "Mint",
            minLux = 10000,
            maxLux = 25000,
            minPPFD = 150,
            maxPPFD = 300,
            hoursPerDay = "6-8",
            lackSymptoms = listOf("叶片变小", "香气减弱", "茎秆徒长"),
            excessSymptoms = listOf("叶片焦枯", "晒伤"),
            tips = "光照：喜光照充足环境，光照越强香气越浓。\n温度：生长适温15~28℃，稍耐寒，冬季需保持在10℃以上。"
        )
    )
    
    val lightTypes = listOf(
        LightType("ceiling", "吸顶灯", 0.70f),
        LightType("pendant", "吊灯", 0.65f),
        LightType("downlight", "筒射灯", 0.45f)
    )
    
    val maintenanceFactors = listOf(
        MaintenanceFactor("0.8", "清洁 (0.8)"),
        MaintenanceFactor("0.7", "一般 (0.7)"),
        MaintenanceFactor("0.6", "污染 (0.6)"),
        MaintenanceFactor("0.65", "室外 (0.65)")
    )
    
    val roomTypes = listOf(
        RoomType("living", "客厅", 150, 100),
        RoomType("bedroom", "卧室", 150, 75),
        RoomType("dining", "餐厅", 300, 150),
        RoomType("kitchen", "厨房", 300, 100),
        RoomType("bathroom", "卫生间", 150, 100),
        RoomType("study", "书房", 300, 300),
        RoomType("corridor", "走廊", 100, 100),
        RoomType("children", "儿童房", 200, 200),
        RoomType("elderly", "老人房", 200, 200)
    )
    
    private var customPlants: Map<String, Plant> = emptyMap()
    
    fun setCustomPlants(custom: Map<String, Plant>) {
        customPlants = custom
    }
    
    fun getAllPlants(): List<Plant> {
        return plants.map { defaultPlant ->
            customPlants[defaultPlant.id] ?: defaultPlant
        }
    }
    
    fun getPlantById(id: String): Plant? {
        return getAllPlants().find { it.id == id }
    }
}

data class RoomType(
    val id: String,
    val name: String,
    val recommendedLux: Int,
    val standardLux: Int
)
