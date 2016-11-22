function currentTime = getCurrentTime()
%getCurrentTime  Returns the current simulation time in ms.
%   currentTime = getCurrentTime() Returns the current simulation time in 
%   ms. 

%   Copyright 2016 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getCurrentTime.m 31 2016-09-28 15:16:56Z afacostag $

import traci.constants
currentTime = traci.simulation.getUniversal(constants.VAR_TIME_STEP);