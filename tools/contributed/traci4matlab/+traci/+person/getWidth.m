function width = getWidth(personID)
%getWidth Returns the width in m of this person.
%   width = getWidth(PERSONID) Returns the width in m of this person.

%   Copyright 2015 Universidad Nacional de Colombia,
%   Politecnico Jaime Isaza Cadavid.
%   Authors: Andres Acosta, Jairo Espinosa, Jorge Espinosa.
%   $Id: getWidth.m 25 2015-06-25 22:38:10Z afacostag $

import traci.constants
width = traci.person.getUniversal(constants.VAR_WIDTH, personID);