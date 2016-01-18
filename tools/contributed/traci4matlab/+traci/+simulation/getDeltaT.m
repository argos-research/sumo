function deltaT = getDeltaT()
%getDeltaT Returns the time-step of the simulation in ms.
%   deltaT = getDeltaT() Returns the time-step of the simulation in ms. 

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getDeltaT.m 22 2015-04-13 14:20:57Z afacostag $

import traci.constants
deltaT = traci.simulation.getUniversal(constants.VAR_DELTA_T);