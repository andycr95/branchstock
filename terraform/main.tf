resource "aws_ecs_task_definition" "branch_stock_task" {
  family                   = "branch_stock_task" # Name your task
  container_definitions    = <<DEFINITION
  [
    {
      "name": "branch_stock_task",
      "image": "945697255475.dkr.ecr.us-east-1.amazonaws.com/branchstock",
      "essential": true,
      "portMappings": [
        {
          "containerPort": 8080,
          "hostPort": 8080
        }
      ],
      "memory": 512,
      "cpu": 256
    }
  ]
  DEFINITION
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"   
  memory                   = 512        
  cpu                      = 256        
  execution_role_arn       = "arn:aws:iam::945697255475:role/ecsTaskExecutionRole"
}

resource "aws_alb" "application_load_balancer" {
  name               = "load-balancer-bs"
  load_balancer_type = "application"
  subnets = [
    "subnet-0a6a145523a16a315",
    "subnet-0c176d7c3466533b2"
  ]
  security_groups = ["sg-0ee3c5b44c4b34550"]
}

resource "aws_lb_target_group" "target_group" {
  name        = "target-group"
  port        = 80
  protocol    = "HTTP"
  target_type = "ip"
  vpc_id      = "vpc-05546231f9fdf0481"
  health_check {
    path = "/docs"
    port = "80"
  }
}

resource "aws_lb_listener" "listener" {
  load_balancer_arn = "${aws_alb.application_load_balancer.arn}"
  port              = "80"
  protocol          = "HTTP"
  default_action {
    type             = "forward"
    target_group_arn = "${aws_lb_target_group.target_group.arn}"
  }
}

resource "aws_ecs_service" "app_service" {
  name            = "branch-stock-service"
  cluster         = "devBranch"
  task_definition = "${aws_ecs_task_definition.branch_stock_task.arn}"
  launch_type     = "FARGATE"
  desired_count   = 1

  load_balancer {
    target_group_arn = "${aws_lb_target_group.target_group.arn}"
    container_name   = "${aws_ecs_task_definition.branch_stock_task.family}"
    container_port   = 8080
  }

  network_configuration {
    subnets          = [ "subnet-0a6a145523a16a315", "subnet-0c176d7c3466533b2"]
    assign_public_ip = true
    security_groups  = ["sg-0ee3c5b44c4b34550"]
  }
}

output "app_url" {
  value = aws_alb.application_load_balancer.dns_name
}