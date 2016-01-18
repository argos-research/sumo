function currentTime = getCurrentTime()
%getCurrentTime  Returns the current simulation time in ms.
%   currentTime = getCurrentTime() Returns the current simulation time in 
%   ms. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getCurrentTime.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
currentTime = traci.simulation.getUniversal(constants.VAR_TIME_STEP);